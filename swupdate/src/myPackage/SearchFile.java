/*
 * Student Name: Seyed Saeed Ghiassy
 * Student ID  : 05994390
 * Supervisor  : Dr.Fakas
 * Project Name: Peer-to-Peer File Sharing Application Using JXTA technology
 * Unit Name   : Final Year Project
 * Unit Code   : 63CP3261
 * DeadLine    : 21-April-2008
 * University  : Manchester Metropolitan University
 * E-mail      : seyed.ghiassy@student.mmu.ac.uk
 * Softwares   : JXTA Version 2.4.1, JDK Version 1.6.0_05, NetBeans IDE 5.5
 */
package myPackage;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.Hashtable;

import javax.swing.DefaultCellEditor;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
//import jxta libraries
import net.jxta.peergroup.PeerGroup;
import net.jxta.share.ContentAdvertisement;
import net.jxta.share.ContentId;
import net.jxta.share.client.CachedListContentRequest;

//This class will search for specifed contents through SaEeD Group
public class SearchFile extends Thread
{
    //Defining Class Variables
    private JTextArea log=null;
    private PeerGroup SaEeDGroup=null;
    private String searchValue =null;
    protected ListRequestor reqestor =null;
    private JTable table = null;
    
    public static ContentAdvertisement [] contents=null;
    
    private boolean running = true;
    
    public SearchFile(PeerGroup group,String searchKey, JTextArea log, JTable table) 
    {
        this.SaEeDGroup = group;
        this.searchValue = searchKey;
        this.log = log;
        this.table = table;
        
    }
    public void run() //cause this thread to execute as long as needed to find 
    {                 // the Contents  
        while(true)
        {
        if(running == false){
            break;
        }
        reqestor = new ListRequestor(SaEeDGroup,searchValue,log, table);        
        reqestor.activateRequest();
        
            try{
            Thread.sleep(10*1000); //Time out for each search through network
            stopThread();
            } catch(InterruptedException ie)
            {
                stopThread();
            }
        }
        log.append("[-]Searching for content is finished.\n");
     }        
    
    public void stopThread() //This method will stop search Process
    {
    running = false;
    if (reqestor != null){
    reqestor.cancel();
    }
    
}
    public void killThread() //This method will Terminate the Search Thread
    {
        log.append("[-]Searching Thread is stopping.\n");
        stopThread();
        
    }
    public ContentAdvertisement [] getContentAdvs() //Accessor to show found contents
    {
        return reqestor.searchResult;
    }
}
//inner class for search
class ListRequestor extends CachedListContentRequest
{
    public static ContentAdvertisement [] searchResult = null;
    private JTextArea log = null;
    private JTable table =null;
    private PeerGroup LRGroup = null;
    DownloadFile [] df;
    private int r_count = 0;
    
    public ListRequestor(PeerGroup SaEeDGroup , String SubStr, JTextArea log,JTable table){
        super(SaEeDGroup,SubStr);
        this.log = log;
        this.table = table;
        this.LRGroup = SaEeDGroup;
    }

    @Override
    public void cancel() {
    	// TODO Auto-generated method stub
    	super.cancel();
    	for(int i=0;i<r_count;i++)
    	{
    		df[i].stopThread();
    	} 	
    }
    
    public void notifyMoreResults() //this method will notify user when new contents are found
    {
        log.append("[+]Searching for More Contents.\n");
        try{
        	searchResult = getResults();
        
        }catch(Exception e)
        {
        	System.out.println("Get Results Prob !");
        }
        
        //clearing the old results
        DefaultTableModel dm = (DefaultTableModel)table.getModel();
        dm.getDataVector().removeAllElements();
        
        //add new contents to Search table
        r_count = searchResult.length;
        df = new DownloadFile[r_count];
        for(int i=0; i < searchResult.length;i++){
        	
            log.append("[*]Found: " + searchResult[i].getName()+"\n" +
                    "Size: " + searchResult[i].getLength() + " Bytes\n");
            dm.addRow(new Object [] {searchResult[i].getName(),searchResult[i].getType(), searchResult[i].getLength(),searchResult[i].getDescription(), 0}); 
            df[i] = new DownloadFile(LRGroup,searchResult[i], new File("file"+i+".mp3"), table,i,4,log);
            df[i].start();
        }
    }
    public ContentAdvertisement [] getContentAdvs()//acessor to return contents
    { 
        return searchResult;
    }

    private Integer checkMinMax(Integer value) {
        int intValue = value.intValue();
        if (intValue < 0) {
          intValue = 0;
        } else if (100 < intValue) {
          intValue = 100;
        }
        return new Integer(intValue);
      }
    }

    

