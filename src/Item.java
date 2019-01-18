
public class Item {	
	String name;
	int price;
	int weight;
	int amount;
	int discount;
	
	
	public Item() {
	}
	
	public Item(String name, int price, int i_weight, int amount) {
		this.name = name;
		this.price = price;
		this.weight = i_weight;
		this.amount = amount;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	public int getDiscount() {
		return discount;
	}

	@Override
	public String toString() {
		return  amount + "x " + name + ", price:" + (price*(100-discount)/100) + ", discount: " + discount + "%";
	}
	

}
