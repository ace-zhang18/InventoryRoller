
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class Driver {
	
	private static final int MAX_POOL_SIZE = 200;
	private static final int MAX_DISCOUNT_SIZE = 20;
	
    public static void main( String[] args )
    {
    	MainWindow window = new MainWindow();
        System.out.println("Starting...");
    }
    
    public static String calculate_inventory(String input, String output, int no_sale_items) {
    	String xslt = "data_template.xslt";
    	File datafile = simpleTransform(input, xslt, output);
    	Scanner datastream = null;
    	try {
			datastream = new Scanner(datafile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Item[] inventory_pool = new Item[MAX_POOL_SIZE];
    	Random rand = new Random();
    	String s = datastream.nextLine();

    	

    	//process master discount if its there
    	boolean master_discount_active = false;
    	int[] discount = new int[MAX_DISCOUNT_SIZE];
    	int[] d_weight = new int[MAX_DISCOUNT_SIZE];
    	int no_discounts = 0;

    	if(s.equalsIgnoreCase("master_discount")) {
    		master_discount_active = true;
    		s = datastream.nextLine();
    		while(!s.equalsIgnoreCase("item")) {
    			int rate = Integer.parseInt(s);
    			s = datastream.nextLine();
    			int weight = Integer.parseInt(s);

    			discount[no_discounts] = rate;
    			d_weight[no_discounts] = weight;
    			no_discounts++;

    			s = datastream.nextLine();
    		}
    	}

    	String name;
    	int min_price;
    	int max_price;
    	int min_amount;
    	int max_amount;
    	int price;
    	int weight;
    	int amount;

    	int no_items = 0;
    	
    	while(s.equalsIgnoreCase("item")) {
    		s = datastream.nextLine();
    		name = s;

    		min_price = Integer.parseInt(datastream.nextLine());
    		max_price = Integer.parseInt(datastream.nextLine());
    		min_amount = Integer.parseInt(datastream.nextLine());
    		max_amount = Integer.parseInt(datastream.nextLine());
    		weight = Integer.parseInt(datastream.nextLine());
    		

    		price = rand.nextInt(max_price - min_price + 1) + min_price;
    		amount = rand.nextInt(max_amount - min_amount + 1) + min_amount;
    		
    		Item item = new Item (name, price, weight, amount);

    		if(!master_discount_active) {
    			no_discounts = 0;
    			s = datastream.nextLine();
    			while(!s.equalsIgnoreCase("item") && datastream.hasNextLine()) {
    				discount[no_discounts] = Integer.parseInt(s);
    				s = datastream.nextLine();
    				d_weight[no_discounts] = Integer.parseInt(s);
    				if(datastream.hasNextLine()) s = datastream.nextLine();
    			}
    		}else {
    			if(datastream.hasNextLine()) s = datastream.nextLine();
    		}

    		item.setDiscount(assignDiscount(discount, d_weight, no_discounts));
    		inventory_pool[no_items] = item;
    		no_items++;

    	}
    	
    	String inventory = "";
    	for(int i = 0; i < no_sale_items; i++) {
    		int pick = rollWeightedProbability(inventory_pool, no_items);
    		inventory += inventory_pool[pick];
    		inventory += "\n";
    		Item target = inventory_pool[pick];
    		for(int g = pick; g + 1 < no_items; g++) {
    			inventory_pool[g] = inventory_pool[g+1];
    		}
    		no_items--;
    	}
    	return inventory;
    }

	public static File simpleTransform(String sourcePath, String xsltPath, String outputPath) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		File outfile = new File(outputPath);
		try {
			Transformer transformer =
					tFactory.newTransformer(new StreamSource(new File(xsltPath)));

			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(outfile));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return outfile;
		
	}
	
	public static int assignDiscount(int[] values, int[] weights, int elements) {
		int pick = rollWeightedProbability(weights, elements);
		int discount = values[pick];
		return discount;
	}

	public static int rollWeightedProbability(int[] weights, int elements) {
		int result = 0;
		Random rand = new Random();
		int max_roll = 0;
		
		for(int i = 0; i < elements; i++) {
			max_roll += weights[i];
		}
		int roll = rand.nextInt(max_roll);
		
		while(roll > 0) {
			roll -= weights[result];
			if(roll < 0) break;
			result++;
		}
		
		return result;
	}
	
	public static int rollWeightedProbability(Item[] items, int elements) {
		int result = 0;
		Random rand = new Random();
		int max_roll = 0;
		
		for(int i = 0; i < elements; i++) {
			max_roll += items[i].weight;
		}
		int roll = rand.nextInt(max_roll);
		
		while(roll > 0) {
			roll -= items[result].weight;
			if(roll < 0) break;
			result++;
		}
		
		return result;
	}
}
