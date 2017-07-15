/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortcutmanager;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
/**
 *
 * @author Akash
 */
public class ShortcutManager extends JFrame {

    private DefaultListModel model,tmp;
    private JList list;
    private JButton remallbtn;
    private JButton addbtn;
    private JButton renbtn;
    private JButton delbtn;
    private JButton openbtn;
    private boolean saved=false;
    public ShortcutManager() throws IOException {
        
        initUI();
    }

    private void createList() throws IOException {   //list to display shortcuts in app

        model = new DefaultListModel();
        tmp=new DefaultListModel();
       /* model.addElement("Amelie");
        model.addElement("Aguirre, der Zorn Gottes");
        model.addElement("Fargo");
        model.addElement("Exorcist");
        model.addElement("Schindler's list");
       */
     
        
       try {
           FileReader file=new FileReader("mylist_file.txt") ;
           BufferedReader br = new BufferedReader(file);
            String line;
            while ((line = br.readLine()) != null) {
            model.addElement(line);
            
          }file.close();
        }catch(IOException ioe)
        {

        }
       
        list = new JList(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {
                    if(model.isEmpty())return;  //Prevents clicking on empty list and ArrayIndexOutOfBounds Exception -1
                    try {
                        shortcut_opener();
                    } catch (IOException ex) {
                        Logger.getLogger(ShortcutManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    private void save_file() throws IOException  //creates a .txt file and save the shortcuts in it. 
    {
        PrintWriter pw = new PrintWriter(new FileWriter("mylist_file.txt"));  //change the file name as per convinience.
 
	for (int i = 0; i < model.size(); i++) {
		pw.write("\r"+model.getElementAt(i).toString());
	}
         
	pw.close(); 
        
        
    }
   
    private void createMenuBar()   //menu for app window
    {
        
        JMenuBar menubar = new JMenuBar();
        
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        JMenu edit = new JMenu("Edit");
        file.setMnemonic(KeyEvent.VK_F);
        help.setMnemonic(KeyEvent.VK_H);
        edit.setMnemonic(KeyEvent.VK_E);
       
        //File Menu Options
        JMenuItem save_file = new JMenuItem("Save");
        JMenuItem exit_app = new JMenuItem("Exit");
        
        //Help Menu
        JMenuItem readme=new JMenuItem("Readme");
        JMenuItem about = new JMenuItem("About");
        JMenuItem contact = new JMenuItem("Contact Me");
        
        //Edit Menu
        JMenuItem undo = new JMenuItem("Undo");
        
        save_file.setMnemonic(KeyEvent.VK_S);   //Mnemonics for File Options
        exit_app.setMnemonic(KeyEvent.VK_E);
        readme.setMnemonic(KeyEvent.VK_R);
        about.setMnemonic(KeyEvent.VK_A);
        
        KeyStroke undo_keystroke= KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
        undo.setAccelerator(undo_keystroke);
        save_file.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
        
        
        save_file.setToolTipText("Save list");  //Tooltips for File Options
        exit_app.setToolTipText("Exit Application");
        readme.setToolTipText("How to use");
        about.setToolTipText("About Shortcut Manager");
        undo.setToolTipText("Revert change to list");
        
        
        exit_app.addActionListener((ActionEvent ae) -> {
          System.exit(0);
        });
        
        readme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             JOptionPane.showMessageDialog(null,"User Guide\n-------------------\n 1) To ADD a new shorcut path click on Add button in the side panel\n 2) TO OPEN any path DOUBLE CLICK /click 'Open Folder' on the path ");    
            }
        });
        save_file.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {if(model.isEmpty())
                {
                 JOptionPane.showMessageDialog(null,"Empty list!");
                }
                    
                save_file();
                saved=true;
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (IOException ex) {
                    Logger.getLogger(ShortcutManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        });
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tmp.isEmpty())
                {
                model.removeAllElements();
                for(int i=0;i<tmp.size();i++)
                    {
                     model.addElement(tmp.getElementAt(i));
                    }
                tmp.clear();
            }
               
        }});
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             JOptionPane.showMessageDialog(null,"Shortcut Manager ver 1.0\n ----------------------\nSimple list to open folder \n ----------------------\nCreator : Akash Singh", "About",JOptionPane.INFORMATION_MESSAGE,null);
            }
        });
        contact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             JOptionPane.showMessageDialog(null,"For any queries or suggestions feel free to contact :\n---------------------------------------------------------------------------\n Mail: conio.iostream.h@gmail.com \n Akash Singh", "Contact Me",JOptionPane.INFORMATION_MESSAGE,null);
           
                
            }
        });
       
        file.add(save_file);
        file.addSeparator();
        file.add(exit_app);
        
        help.add(readme);
        help.add(about);
        help.add(contact);
        
        edit.add(undo);
        
        
        menubar.add(file);
        menubar.add(edit);
        menubar.add(help);
       
        setJMenuBar(menubar);
    }
    private void createButtons() {    //CREATES OPERATIONAL BUTTONS ON RIGHT SIDE OF APP

        remallbtn = new JButton("Remove All");
        addbtn = new JButton("Add");
        renbtn = new JButton("Rename");
        delbtn = new JButton("Delete");
        openbtn = new JButton("Open folder");
        
        addbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String text = JOptionPane.showInputDialog("Add a new path");
                String item = null;

                if (text != null) {
                    item = text.trim();
                } else {
                    return;
                }

                if (!item.isEmpty()) {
                    model.addElement(item);
                }
            }
        });

        delbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ListSelectionModel selmodel = list.getSelectionModel();
                int index = selmodel.getMinSelectionIndex();
                if (index >= 0) {
                     tmp.clear();
                     for(int i=0;i<model.size();i++)
                    {
                     tmp.addElement(model.getElementAt(i));
                    }
                    model.remove(index);
                }
            }

        });

        renbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ListSelectionModel selmodel = list.getSelectionModel();
                int index = selmodel.getMinSelectionIndex();
                if (index == -1) {
                    return;
                }

                Object item = model.getElementAt(index);
                String text = JOptionPane.showInputDialog("Rename path item", item);
                String newitem = null;

                if (text != null) {
                    newitem = text.trim();
                } else {
                    return;
                }

                if (!newitem.isEmpty()) {
                    model.remove(index);
                    model.add(index, newitem);
                }
            }
        });

        remallbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!model.isEmpty())
                {
                int selectedOption = JOptionPane.showConfirmDialog(null,"Remove all shortcuts?", "Choose", JOptionPane.YES_NO_OPTION); 
                if (selectedOption == JOptionPane.YES_OPTION) {
                    tmp.clear();
                    for(int i=0;i<model.size();i++)
                    {
                     tmp.addElement(model.getElementAt(i));
                    }
                    model.clear();
                }
               
            }
               
              
        }});
        
        openbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              if(model.isEmpty())return;  //Prevents clicking on empty list and ArrayIndexOutOfBounds Exception -1
                    try {
                        ListSelectionModel selmodel = list.getSelectionModel();
                        int index = selmodel.getMinSelectionIndex();
                        if (index == -1){
                            return;
                        }
                        shortcut_opener();
                    } catch (IOException ex) {
                        Logger.getLogger(ShortcutManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
            }
        });
    }

    private void initUI() throws IOException {  //INVOKES LISTED METHODS TO CREATE DISPLAY OF APPLICATION USING JSCROLLPANE AND CONTAINER PANE

        
        createList();
       // ImageIcon ic = new ImageIcon("icon.png");  //set icon for application
       // setIconImage(ic.getImage());
        createButtons();
        createMenuBar();
        JScrollPane scrollpane = new JScrollPane(list);
       
        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);
        

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(scrollpane)
                
                .addGroup(gl.createParallelGroup()
                        .addComponent(addbtn)
                        .addComponent(openbtn)
                        .addComponent(renbtn)
                        .addComponent(delbtn)
                        .addComponent(remallbtn)
                        
                )
               
        );

        gl.setVerticalGroup(gl.createParallelGroup(CENTER)
                .addComponent(scrollpane)
                
                .addGroup(gl.createSequentialGroup()
                        .addComponent(addbtn)
                        .addComponent(openbtn)
                        .addComponent(renbtn)
                        .addComponent(delbtn)
                        .addComponent(remallbtn)
                ));

        gl.linkSize(addbtn, renbtn, delbtn, remallbtn,openbtn);
        
        
        
        
        pack();

        setTitle("Shortcut Manager");
        
        
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent we)
    { 
        String ObjButtons[] = {"Yes","No"};
        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Shortcut Manager",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
        if(PromptResult==JOptionPane.YES_OPTION)
        {
            
            if((!saved)&&(!model.isEmpty()))
            {
             int save_decide=JOptionPane.showOptionDialog(null,"List not saved\n Save File?","Save",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
             if(save_decide==JOptionPane.YES_OPTION)
             {
                 try {
                     save_file();
                 } catch (IOException ex) {
                     Logger.getLogger(ShortcutManager.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
            }
            System.exit(0);
        }
    }
});
    }
  
    
    public void list_loader()throws IOException   //LOADS LIST FROM .txt FILE ONTO THE SCROLLPANE
    {
       try (BufferedReader br = new BufferedReader(new FileReader("mylist_file.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
            model.addElement(line); 
          }
        }catch(IOException ioe)
        {
          ioe.printStackTrace();
        }


    }
    public void shortcut_opener() throws IOException   //INVOKED AT DOUBLLE CLICK AND OPEN ACTION ON LIST ITEM
    {
      
            ListSelectionModel selmodel = list.getSelectionModel();
            int index = selmodel.getMinSelectionIndex();
            Object item = model.getElementAt(index);
            String path=item.toString();     
            Desktop desktop = Desktop.getDesktop();
            File dirToOpen = null; 
            try{ 
            dirToOpen = new File(path);
            desktop.open(dirToOpen);
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println("Faulty path name!");
            }   
    }
   
    
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    
                    ShortcutManager sm = new ShortcutManager();   
                    sm.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ShortcutManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
}
