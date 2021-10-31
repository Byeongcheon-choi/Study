import grpc
import example_pb2
import example_pb2_grpc
from concurrent import futures

class Branch(example_pb2_grpc.BranchServicer):
    def __init__(self, id, balance, branches):
        self.id = id
        self.balance = balance
        self.branches = branches
        self.stubList = list()
        self.recvMsg = list()


    def createStubs(self): # Making the branch channel/stub
        for oldid in self.branches:
            if oldid != self.id:
                port = str(50000 + oldid)
                channel = grpc.insecure_channel("localhost:" + port)
                self.stubList.append(example_pb2_grpc.BranchStub(channel))


    def MsgDelivery(self, request, context):     # define the funciton about dealing with the customer/branch request
        return self.DealingMessage(request, True)
    def MsgPropagate(self, request, context):
        return self.DealingMessage(request, False)


    # Handle received Msg, generate and return a MsgResponse
    def DealingMessage(self, request, propagate):
        result = "success"
        statusmessage = dict()

        if request.money < 0:
            result = "fail"

        elif request.interface == "query":  # define a way for query processing
            statusmessage["money"] = request.money # add the money value when query event
            pass

        elif request.interface == "withdraw": # define a way for withdraw processing
            if self.balance >= request.money:
                self.balance -= request.money
                if propagate == True:
                    self.Propagate_Withdraw(request)
            else:
                result = "fail"

        elif request.interface == "deposit": # define a way for deposit processing
            self.balance += request.money
            if propagate == True:
                self.Propagate_Deposit(request)
            else:
                 result = "fail"
        else:
            result = "fail"

        statusmessage["interface"] = request.interface # add rest of information
        statusmessage["result"] = result

        self.recvMsg.append(statusmessage) #add the message to receive message list.

        #return the response to customer with result
        return example_pb2.ResponseMessage(interface=request.interface, result=result, money=self.balance)

    
    def Propagate_Withdraw(self, request): # Decrease the balance with the amount sepcified in branch request
        for stub in self.stubList:
            stub.MsgPropagate(example_pb2.RequestMessage(id=request.id, interface="withdraw", money=request.money))

    def Propagate_Deposit(self, request):  # increase the balance with the amount sepcified in branch request
        for stub in self.stubList:
            stub.MsgPropagate(example_pb2.RequestMessage(id=request.id, interface="deposit", money=request.money))


def serv(branch):
    print("server Start....")
    branch.createStubs()

    #create each server with which RPC can be serviced
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    example_pb2_grpc.add_BranchServicer_to_server(branch, server)
    port = str(50000 + branch.id)

    #opens an insecrue port for accepting RPC
    server.add_insecure_port("[::]:" + port)

    #START THIS SERVER
    server.start()

    #BLOCK current thread until the server stops
    server.wait_for_termination()


