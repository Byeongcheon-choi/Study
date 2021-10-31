import json
import grpc
import example_pb2, example_pb2_grpc
import Branch,Customer
import multiprocessor



# # starting the gRPC process by using input data
def startingprocess(input_data):

    ids = list()
    CustomerClass = list()
    Branchclass = list()
    branchlist = list()
    customerList = list()

    # make a Branch class for each input data
    for i in input_data:
        if i["type"] == "branch":
            bclass = Branch.Branch(i["id"], i["balance"], ids)
            Branchclass.append(bclass)
            ids.append(bclass.id)

    # make a Customer class for each input data
    for s in input_data:
        if s["type"] == "customer":
            cclass = Customer.Customer(s["id"], s["events"])
            CustomerClass.append(cclass)
  
    # working branch/customer processes in parallel
    branchlist = multiprocessor.multibranch(Branchclass)
    customerList = multiprocessor.multicustomer(CustomerClass)
  

    for c in customerList:# Waiting to complete customer process
        c.join()

   
    for b in branchlist: # Terminate each of Branch processes
        b.terminate()
 

if __name__ == "__main__":
    # input the path of input file location
    path = input("type file location : ")

    try:
        #open the json file
        with open(path, 'r') as f:
            input_data = json.load(f)
        #start to make a output text file
        open("output.txt", "w").close()

        # starting the gRPC process
        startingprocess(input_data)
    
    #define the exceptino cases
    except FileNotFoundError:
        print("Could not find input file")