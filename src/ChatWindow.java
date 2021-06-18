import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class ChatWindow extends JFrame {

    private JPanel header;
    private JLabel userName;
    private JPanel messageArea;
    private JPanel footer;
    private JTextField messageTextField;
    private JButton sendButton;
    private JLabel close;
    private String user;
    private final Box messageBox = Box.createVerticalBox() ;

    public ChatWindow(String user) {
        this.user = user;

        this.setSize(400, 600);
        this.setTitle("Chat Window");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        /* HEADER - USER INFO */
        initHeader();

        /* MAIN DISPLAY PANEL */
        initMessageBox();

        /* MESSAGE BOX PANEL */
        initChatBox();

        /* ADD COMPONENTS */
        this.add(header);
        this.add(messageArea);
        this.add(footer);


        this.setVisible(true);

    }

    private void initHeader() {

        header = new JPanel();
        header.setSize(400, 50);
        header.setBackground(new Color(59, 124, 255));
        header.setLayout(null);

        userName = new JLabel(user);
        userName.setBounds(150, 0, 100, 50);
        userName.setFont(new Font("Helvetica", Font.BOLD, 24));
        header.add(userName);

    }

    private void initMessageBox() {


        messageArea = new JPanel();
        messageArea.setLayout(new BorderLayout());

        messageArea.setBounds(10, 60, 360, 440);
        messageArea.setFont(new Font("Helvetica", Font.PLAIN, 24));


    }

    private void initChatBox() {

        footer = new JPanel();
        footer.setBounds(0, 510, 400, 50);
        footer.setLayout(null);
        footer.setBackground(new Color(59, 124, 255));
        footer.setVisible(true);


        this.messageTextField = new JTextField();
        messageTextField.setBounds(10, 10, 280, 30);
        messageTextField.setFont(new Font("Helvetica", Font.PLAIN, 18));

        sendButton = new JButton();
        sendButton.setBounds(300, 10, 75, 30);
        sendButton.setText("Send");
        sendButton.setFocusable(false);

        footer.add(messageTextField);
        footer.add(sendButton);
    }

    public JButton getSendButton() {
        return sendButton;
    }


    public JTextField getMessageBox() {
        return messageTextField;
    }
    public void addSendersMessage(String message){

        JPanel rightSide = new JPanel(new BorderLayout());

        JPanel box = formatMessage(message,false);

//        JLabel text = (JLabel) box.getComponent(0);
//        text.setBackground(new Color(0x15D4EF, true));
//        text.setOpaque(true);

        rightSide.add(box, BorderLayout.LINE_END);

        messageBox.add(rightSide);

        messageArea.add(messageBox,BorderLayout.PAGE_START);

        this.revalidate();

    }
    public void addReceiversMessage(String message){

        JPanel leftSide = new JPanel(new BorderLayout());

        JPanel box = formatMessage(message,true);

        leftSide.add(box, BorderLayout.LINE_START);

        messageBox.add(leftSide);

        messageArea.add(messageBox,BorderLayout.PAGE_START);

        this.revalidate();
    }

    private JPanel formatMessage(String message, boolean receiver){
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box,BoxLayout.Y_AXIS));

        JLabel text = new JLabel("<html><p style=\"width: 130px\">" + message + "</p></html>");
        if(receiver)
            text.setBackground(new Color(0x33F5CD));
        else
            text.setBackground(new Color(0x20FA72));
        text.setOpaque(true);
        text.setBorder(new EmptyBorder(10,10,10,10));
        box.add(text);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        JLabel currentTime = new JLabel(formatter.format(new Date().getTime()));
        currentTime.setFont(new Font("Helvetica",Font.PLAIN,10));

        box.add(currentTime);

        return box;
    }
}
