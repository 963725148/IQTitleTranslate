import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// GUI
public class MyFrame {
    public static void main(String[] args) {
        new MyFrame();
    }
    JButton open = null;
    public MyFrame() {
        JFrame win = new JFrame("Title Translate");
        Container ct = new Container();
        ct.setLayout(new FlowLayout());
        JPanel jp = new JPanel();
        open = new JButton("选择SQL文件并转换翻译");
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        jp.add(open);
        jp.add(new JLabel(" "));
        jp.add(new JLabel(" "));
        JLabel jl1 = new JLabel();
        jp.add(jl1);
        open.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        // 获取文件
                        FileSystemView fsv = FileSystemView.getFileSystemView();
                        File desktop = fsv.getHomeDirectory();
                        JFileChooser jfc = new JFileChooser(desktop);
                        SQLFileFilter sqlFilter = new SQLFileFilter();
                        jfc.addChoosableFileFilter(sqlFilter);
                        jfc.setFileFilter(sqlFilter);
                        jfc.setMultiSelectionEnabled(true);
                        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        jfc.showDialog(new JLabel(), "选择");
                        if (jfc.getSelectedFile() != null) {
                            File[] file = jfc.getSelectedFiles();
                            for (int i = 0; i < file.length; i++) {
                                String args[] = new String[3];
                                args[0] = file[i].getAbsolutePath();
                                args[1] = file[i].getName().split("\\.")[0]+"_title_translate";
                                args[2] = file[i].getParent()+"/"+args[1]+".sql";
                                Main.main(args);
                                jl1.setText(args[1]+"翻译成功");
                            }
                        }
                    }

                }
        );
        ct.add(jp);
        win.add(ct);
        win.setSize(400, 150);
        win.setLocation(300, 50);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
    }
    class SQLFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            String name = f.getName();
            return f.isDirectory()
                    || name.toLowerCase().endsWith(".sql");
        }

        @Override
        public String getDescription() {
            return "*.sql";
        }

    }
}
