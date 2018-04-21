package koi.ui.orders;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import koi.core.DrinkItem;

@SuppressWarnings("serial")
public class CartTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int DRINK_SERIAL = 0;
	private static final int DRINK_CODE_COL = 1;
	private static final int DRINK_NAME_COL = 2;
	private static final int QUANTITY_COL = 3;
	private static final int DRINK_SUB_TOTAL = 4;
	
	private String columnNames[] = {"Drink Serial", "Drink Code", "Drink Name", "Quantity", "Drink SubTotal"};
	
	private List<DrinkItem> cartItems;
	
	public CartTableModel(List<DrinkItem> list){
		cartItems = list;
	}
	
	public int getColumnCount(){
		return columnNames.length;
	}
	
	public int getRowCount(){		
		return cartItems.size();
	}
	
	public String getColumnName(int col){
		return columnNames[col];
	}
	
	public Object getValueAt(int row, int col){
		DrinkItem tempDrink = cartItems.get(row);
		switch(col){		
			case DRINK_SERIAL:
				return row+1;
			case DRINK_CODE_COL:
				return tempDrink.getDrinkCode();
			case DRINK_NAME_COL:
				return tempDrink.getDrinkName();
			case QUANTITY_COL:
				return tempDrink.getQuantity();
			case DRINK_SUB_TOTAL:
				return tempDrink.getQuantity() * tempDrink.getPrice();
			case OBJECT_COL:
				return tempDrink;
			default:
				return tempDrink.getDrinkName();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int col){
		return getValueAt(0, col).getClass();
	}
}
