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

import java.io.File;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.plaf.ProgressBarUI;
import net.jxta.peergroup.PeerGroup;
import net.jxta.share.ContentAdvertisement;
import net.jxta.share.client.GetContentRequest;

//This class runs as Thread and start Downloading the File as soon as called
public class DownloadFile extends Thread
{
    private PeerGroup SaEeDGroup=null;
    protected GetRemoteFile myDownloader = null;
    private JTextArea log;
    private JTable urTable = null;
    private int row,column;
    private ContentAdvertisement adv;
    private File dest;
    private JProgressBar bar;
    public DownloadFile(PeerGroup group, ContentAdvertisement contentAdv, File destination , JTextArea log,
                         JProgressBar progress) 
    {
        this.log = log;
        this.bar = progress;
        this.dest = destination;
        this.adv = contentAdv;
        this.SaEeDGroup = group;
        this.log.append("[+]Starting Download Object.\n");
        //inner classes used here for better performance
         
        System.out.println("Destination is :: " + destination.getAbsolutePath());
        
    }
    
    public void stopThread()
    {
    	myDownloader.cancel();
    }
    
    @Override
    public void run() {
    	// TODO Auto-generated method stub
    	myDownloader = new GetRemoteFile(SaEeDGroup, adv, dest, this.log, this.urTable,this.row,this.column);
    	
    }
    
    public DownloadFile(PeerGroup group, ContentAdvertisement contentAdv, File destination , JTable table,
            int trow,int tcol , JTextArea log) 
    {
    	this.log = log;	
    this.urTable = table;
    this.row = trow;
    this.column = tcol;
    this.log = log;
    this.dest = destination;
    this.adv = contentAdv;
    this.SaEeDGroup = group;
	this.log.append("[+]Starting Download Object.\n");
	//inner classes used here for better performance
	 
	System.out.println("Destination is :: " + destination.getAbsolutePath());

	}
    
}
//inner class which handles download requestes
class GetRemoteFile extends GetContentRequest
{
    private JTable urTable = null;
    int row,col;
	private JProgressBar progressBar = null;
    private JTextArea log =null;
    private boolean downloadFinished = false;
    public GetRemoteFile(PeerGroup group, ContentAdvertisement contentAdv, File destination , JTextArea log,
                         JProgressBar progress)
    {
        super(group, contentAdv, destination);
        this.progressBar = progress;
        this.log = log;
        this.log.append("[+]Download in Progress.\n");
    }
    
    public GetRemoteFile(PeerGroup group, ContentAdvertisement contentAdv, File destination , JTextArea log,
            JTable table,int trow,int tcol)
    {
    	super(group, contentAdv, destination);
	this.urTable= table;
	row = trow;
	col = tcol;
	this.log = log;
	this.log.append("[+]Download in Progress.\n");
	}
    
    public void notifyUpdate(int percentage) //this method will notify about download progress
    {
      //progressBar.setValue(percentage);
    	urTable.setValueAt(percentage, row, col);
    }
    
    public void notifyDone()//this method will return message about download process 
    {
        log.append("[+]Donwloading Process is sucessfully finished.\n");
    }

    public void notifyFailure()//this method will return message if download failed 
    {
        log.append("[-]Downloading File is Failed!!!!!\n");
    }

}
