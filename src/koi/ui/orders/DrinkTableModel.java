package koi.ui.orders;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import koi.core.DrinkItem;

@SuppressWarnings("serial")
public class DrinkTableModel extends AbstractTableModel {
		
		public static final int OBJECT_COL = -1;
		private static final int DRINK_CODE_COL = 0;
		private static final int DRINK_NAME_COL = 1;
		private static final int DRINK_CATEGORY_COL = 2;
		private static final int PRICE_COL = 3;
		private static final int QUANTITY_COL = 4;
		
		private String columnNames [] = { "Drink Code", "Drink Name", "Drink Category",
						"Price", "Quantity" };
		private List<DrinkItem> drinkItems;
		
		public DrinkTableModel(List<DrinkItem> drinkItems) {
			this.drinkItems = drinkItems;
		}
		
		public int getColumnCount(){
			return columnNames.length;
		}
		
		public int getRowCount(){		
			return drinkItems.size();
		}
		
		public String getColumnName(int col){
			return columnNames[col];
		}
		
		public Object getValueAt(int row, int col){
			DrinkItem tempDrink = drinkItems.get(row);
			switch(col){
			case DRINK_CODE_COL:
				return tempDrink.getDrinkCode();
			case DRINK_NAME_COL:
				return tempDrink.getDrinkName();
			case DRINK_CATEGORY_COL:
				return tempDrink.getDrinkCategory();
			case PRICE_COL:
				return tempDrink.getPrice();
			case QUANTITY_COL:
				return tempDrink.getQuantity();
			case OBJECT_COL:
				return tempDrink;
			default:
				return tempDrink.getDrinkName();
			}
		}
		
		//select
		public Class getColumnClass(int col){
			return getValueAt(0, col).getClass();
		}
		
}
