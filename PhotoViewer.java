package photoviewer;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.Timer;
public class PhotoViewer extends JFrame
{
    
    JMenu file,edit,setting,help;
    JMenuBar mb;
    JMenuItem open,openFolder,exit,copy,cut,paste;
    JPanel panel,panelLeft,panelImage,panelControl;
    JSplitPane splitPaneV,splitPaneH;
    JList list;
    DefaultListModel model;
    JLabel j1;
    ImageIcon img;
    JFrame jf,jfFullScreen;
    JFileChooser FileChooser,FolderChooser;
    int listCount;
    JButton btnNext,btnPrev,btnStart,btnStop,btnFullscreen;
    Timer timer;
    public PhotoViewer()
    {
        setFrameWindow();
        createMenu();
        initComp();
    }
    
    final void createMenu()
    {
        mb=new JMenuBar();
        setJMenuBar(mb);
        
        file=new JMenu ("File");
        mb.add(file);
        
        edit=new JMenu ("Edit");
        mb.add(edit);
        
        setting=new JMenu ("Settings");
        mb.add(setting);
        
        help=new JMenu ("Help");
        mb.add(help);
        
        open=new JMenuItem("Open");
        file.add(open);
        
        file.addSeparator();
        
        openFolder=new JMenuItem("Open Folder");
        file.add(openFolder);
        
        file.addSeparator();
        
        exit=new JMenuItem("Exit");
        file.add(exit);
        
        cut=new JMenuItem("Cut");
        edit.add(cut);
       edit.addSeparator();
        
        copy=new JMenuItem("Copy");
        edit.add(copy);
        edit.addSeparator();
        
        paste=new JMenuItem("Paste");
        edit.add(paste);
            
        open.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                    FileChooser=new JFileChooser();
                    FileChooser.setCurrentDirectory(new java.io.File("."));
                    FileChooser.setDialogTitle("Select Image File");
                      
                    if(FileChooser.showOpenDialog(jf)==JFileChooser.APPROVE_OPTION)
                      {
                          String path=FileChooser.getSelectedFile().toString();
                          img= new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(500, 300,Image.SCALE_DEFAULT ));
                          j1.setIcon(img);
                      }
            }
        });
        
        openFolder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                    
                    FolderChooser=new JFileChooser();
                    FolderChooser.setCurrentDirectory(new java.io.File("."));
                    FolderChooser.setDialogTitle("Select Folder");
                    FolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    if(FolderChooser.showOpenDialog(jf)==JFileChooser.APPROVE_OPTION)
                    {
                        File folder=new File(FolderChooser.getSelectedFile().toString());
                        File[] listOfFiles=folder.listFiles();
                        model.removeAllElements();
                        for(File file : listOfFiles)
                        {
                            if(file.isFile())
                            {
                                model.addElement(file.getPath());
                            }
                        }
                        listCount=listOfFiles.length;
                        list.setSelectedIndex(0);
                    }
            }
        });
        
    }
    final void initComp()
    {
        model=new DefaultListModel();
        list =new JList(model);
        panelLeft.add(list,BorderLayout.CENTER);
        
        j1=new JLabel();
        panelImage.add(j1,BorderLayout.CENTER);
        list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                    img=new ImageIcon(new ImageIcon(list.getSelectedValue().toString()).getImage().getScaledInstance(500, 300,Image.SCALE_DEFAULT ));
                    j1.setIcon(img);
            }
        });
        
        btnNext=new JButton("Next");
        panelControl.add(btnNext);
        btnNext.addActionListener(new ActionListener() 
        {
       
            @Override
            public void actionPerformed(ActionEvent e) {
           if(list.getSelectedIndex()>=0&&list.getSelectedIndex()<listCount)
           {
               list.setSelectedIndex(list.getSelectedIndex()+1);
           }
          
           
        }
        });
        btnPrev=new JButton("Prev");
        panelControl.add(btnPrev);
        btnPrev.addActionListener(new ActionListener() 
        {
       
            @Override
            public void actionPerformed(ActionEvent e) {
           if(list.getSelectedIndex()>=0&&list.getSelectedIndex()<listCount)
           {
               list.setSelectedIndex(list.getSelectedIndex()-1);
           }
            }
        });
        
        btnStart=new JButton("Start");
        panelControl.add(btnStart);
        btnStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                    timer.start();
            }
        });
        timer = new Timer(2000, null);
        timer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(list.getSelectedIndex()>=0&&list.getSelectedIndex()<listCount)
           {
               list.setSelectedIndex(list.getSelectedIndex()+1);
           }
            }
        });
        btnFullscreen=new JButton("Full Screen");
        panelControl.add(btnFullscreen);
        btnFullscreen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jfFullScreen= new  JFrame();
                
            jfFullScreen.setUndecorated(true);
            jfFullScreen.setVisible(true);
            
            jfFullScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            }
        });
        
    }
     final void setFrameWindow()
     {
            jf=this;
            setSize(800,600);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setTitle("Photo Viewer");
            
            panel = new JPanel();
            panel.setLayout(new BorderLayout());
            add(panel);
            
            panelLeft=new JPanel();
            panelLeft.setLayout(new BorderLayout());
            
            
            panelImage=new JPanel();
            panelImage.setLayout(new BorderLayout());
            
            panelControl=new JPanel();
            panelControl.setLayout(new FlowLayout());
            
            
            splitPaneV= new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPaneV.setDividerLocation(700);
            splitPaneV.setLeftComponent(panelImage);
            splitPaneV.setRightComponent(panelControl);

            
            splitPaneH= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitPaneH.setDividerLocation(200);
            splitPaneH.setLeftComponent(panelLeft);
            splitPaneH.setRightComponent(splitPaneV);
            panel.add(splitPaneH,BorderLayout.CENTER);
            
            initComp();
     }
     
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                    new PhotoViewer();
            }
        });
       
    }
    
}
