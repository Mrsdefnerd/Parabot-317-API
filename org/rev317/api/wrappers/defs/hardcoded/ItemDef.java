package org.rev317.api.wrappers.defs.hardcoded;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.parabot.core.ui.components.LogArea;

public class ItemDef {
	
	public static String getItemName(int id) {
		for (int j = 0; j < ItemList.length; j++) {
			if (ItemList[j] != null)
				if (ItemList[j].itemId == id)
					return ItemList[j].itemName;
		}
		return null;
	}
	
	public static ItemList ItemList[] = new ItemList[20168];
	public static void newItemList(int ItemId, String ItemName) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 0; i < 11740; i++) {
			if (ItemList[i] == null) {
				slot = i;
				break;
			}
		}

		if (slot == -1)
			return; // no free slot found
		ItemList newItemList = new ItemList(ItemId);
		newItemList.itemName = ItemName;
		ItemList[slot] = newItemList;
	}
	public static boolean loadItemList() {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[1];
		for (String s : loadLines("http://epic-pk.com/Parabot/item.cfg")) {
			line = s.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");

				if (token.equals("item")) {
					newItemList(Integer.parseInt(token3[0]),
							token3[1].replaceAll("_", " "));
				}
			}
		}
		LogArea.log("Item def loaded!");
		return false;
	}

	private static ArrayList<String> loadLines(String url) {
		ArrayList<String> list = new ArrayList<String>();
		try {
	        URL oracle = new URL(url);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));
	
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            list.add(inputLine);
	        in.close();
		} catch (Exception e) {}
        return list;
	}

}
