import time
import grpc
import example_pb2
import example_pb2_grpc

# Make a Custmoer server
class Customer:

    def __init__(self, id, events): # Make a Customer class variants
        self.id = id
        self.events = events
        self.recvMsg = list()
        self.stub = None

    def createStub(self):  # Making the client channel/stub
        port = str(50000 + self.id)
        channel = grpc.insecure_channel('localhost:' + port)
        self.stub = example_pb2_grpc.BranchStub(channel)

    def executeEvents(self): # Make a executing function
        
        for i in self.events: # Make a function of each input data
            if i["interface"] == "query": # sleep the 3 second for query to guarantee all the updates are propagated among the Branch processes
                time.sleep(3)
            
            # Request it and got the response from Branch server
            r = self.stub.MsgDelivery(example_pb2.RequestMessage(id=i["id"], interface=i["interface"], money=i["money"]))
            result = {"interface": r.interface, "result": r.result} # Make a message to 

            if result["interface"] == "query": # if the interface value is query, add money value
                result["money"] = r.money

            
            self.recvMsg.append(result) # add to recieve list for getting output value

    def final(self): # Make a output message
        finalresult = {"id" : self.id, "recv" : self.recvMsg}
        return finalresult


# create the Customer server
def Cus(customer):
    print("Create the Customer server..")
    customer.createStub()
    customer.executeEvents()

    print("customer start...")
    output = customer.final() # get the result value
    print(output)
    #write the result on Output text file
    output_file = open("output.txt", "a")
    output_file.write(str(output) + "\n")
    output_file.close()


