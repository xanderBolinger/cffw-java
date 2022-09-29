package UtilityClasses;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Conflict.Game;
import Conflict.OpenUnit;
import Trooper.Trooper;
import Unit.EditUnit;


/*Description: uses mouse click and drag listeners to reorder a units individuals. Takes a unit's individuals array list and the list model.

 	Implemented Like this: 
 	// Check refresh individuals to see it toStringImproved is using a reference to game
 	list = new JList(UnitReorderListener.getListModel(entries, game));
	MouseAdapter listener = new UnitReorderListener(list, entries);
    list.addMouseListener(listener);
    list.addMouseMotionListener(listener);
	scrollPane.setViewportView(list);
	
 */
public class UnitReorderListener extends MouseAdapter {

	   private JList list;
	   private int pressIndex = 0;
	   private int releaseIndex = 0;
	   private ArrayList<Trooper> entries;  
	   private EditUnit editUnit = null; 
	   private OpenUnit openUnit = null; 
	   
	   public UnitReorderListener(JList list, ArrayList<Trooper> entries, EditUnit editUnit) {
	      if (!(list.getModel() instanceof DefaultListModel)) {
	         throw new IllegalArgumentException("List must have a DefaultListModel");
	      }
	      
	      this.entries = entries; 
	      this.editUnit = editUnit; 
	      this.list = list;
	   }
	   
	   public UnitReorderListener(JList list, ArrayList<Trooper> entries, OpenUnit openUnit) {
		      if (!(list.getModel() instanceof DefaultListModel)) {
		         throw new IllegalArgumentException("List must have a DefaultListModel");
		      }
		      
		      this.entries = entries; 
		      this.openUnit = openUnit; 
		      this.list = list;
		   }
	
	   @Override
	   public void mousePressed(MouseEvent e) {
	      pressIndex = list.locationToIndex(e.getPoint());
	   }
	
	   @Override
	   public void mouseReleased(MouseEvent e) {
	      releaseIndex = list.locationToIndex(e.getPoint());
	      if (releaseIndex != pressIndex && releaseIndex != -1) {
	         reorder();
	         if(editUnit != null) {
	        	 editUnit.refreshIndividuals();
	         } else {
	        	 openUnit.refreshIndividuals();
	         }
	      }
	   }
	
	   @Override
	   public void mouseDragged(MouseEvent e) {
	      mouseReleased(e);
	      pressIndex = releaseIndex;      
	   }
	
	   private void reorder() {
	      DefaultListModel model = (DefaultListModel) list.getModel();
	      Object dragee = model.elementAt(pressIndex);
	      model.removeElementAt(pressIndex);
	      model.insertElementAt(dragee, releaseIndex);
	      
	      Trooper element = entries.remove(pressIndex);
	      entries.add(releaseIndex, element);
	      
	      if(openUnit != null) {
	    	  for(int i = 0; i < entries.size(); i++) {
		    	  entries.get(i).number = i + 1; 
			  }
	      }
	     
	   }
	   
	   public static DefaultListModel getListModel(ArrayList<Trooper> arrayList, Game game) {
		   
			DefaultListModel listModel = new DefaultListModel();
			
			for(Trooper t : arrayList) {
				listModel.addElement(t.toStringImproved(game));
			}
			
			return listModel; 
		}

}
	
