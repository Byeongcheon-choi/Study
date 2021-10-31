import multiprocessing
import Customer, Branch


#Do the muliprocessing method for making the reponse processor faster
#go all branch class to the each server in a parallel way.

def multibranch(Branchclass):
        branchlist = []
        for a in Branchclass:
            fbranch = multiprocessing.Process(target=Branch.serv, args=(a,))
            branchlist.append(fbranch)
            fbranch.start()
        return branchlist

def multicustomer(CustomerClass):
        customerList = []
        for b in CustomerClass:
            fcustomer = multiprocessing.Process(target=Customer.Cus, args=(b,))
            customerList.append(fcustomer)
            fcustomer.start()
        return customerList