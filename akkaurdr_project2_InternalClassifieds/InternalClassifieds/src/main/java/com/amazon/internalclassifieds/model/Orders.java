package com.amazon.internalclassifieds.model;

public class Orders {
	//Attributes
		public int orderID;
		public int classifiedID;
		public int fromUserID;
		public int toUserID;
		public int proposedPrice;
		public int status;
		public String lastUpdatedOn;
		
		public Orders() {
		}	

		public Orders(int orderID, int classifiedID, int fromUserID, int toUserID, int proposedPrice, int status,
				String lastUpdatedOn) {
			this.orderID = orderID;
			this.classifiedID = classifiedID;
			this.fromUserID = fromUserID;
			this.toUserID = toUserID;
			this.proposedPrice = proposedPrice;
			this.status = status;
			this.lastUpdatedOn = lastUpdatedOn;
		}
		
		// To print the order summary, i.e. all the transactions that have occurred
		public void prettyPrint(Orders order) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Order ID:\t\t"+order.orderID);
			System.out.println("Classified ID:\t\t"+order.classifiedID);
			System.out.println("Sold by- UserID:\t"+order.fromUserID);
			System.out.println("Bought by- UserID:\t"+order.toUserID);
			System.out.println("Price:\t\t\t"+order.proposedPrice);
			System.out.println("last updated on:\t"+order.lastUpdatedOn);
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		}

		@Override
		public String toString() {
			return "Orders [orderID=" + orderID + ", classifiedID=" + classifiedID + ", fromUserID=" + fromUserID
					+ ", toUserID=" + toUserID + ", proposedPrice=" + proposedPrice + ", status=" + status
					+ ", lastUpdatedOn=" + lastUpdatedOn + "]";
		}
		
}
