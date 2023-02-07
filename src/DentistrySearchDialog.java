import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DentistrySearchDialog extends JDialog implements ActionListener {
    private static HashMap<String, String> fields = new HashMap<String, String>();

    private static final String[] headers = {"Название", "Адрес", "Заведующий клиникой"};
    private static final String SAVE = "Сохранить";
    private static final String CANCEL = "Закрыть";
    private static final int PAD = 10;
    private static final int W_L = 100;
    private static final int W_T = 300;
    private static final int W_B = 120;
    private static final int H_B = 25;

    private final JTextPane search = new JTextPane();
    JComboBox<String> field_to_find;
    private boolean save = false;

    public DentistrySearchDialog(){
        setLayout(null);
        buildFields();
        buildButtons();
        setModal(true);
        setResizable(false);
        setBounds(300, 300, 450, 150);
        setVisible(true);
    }

    public void buildFields() {
        fields.put("Название", "dentistry_name");
        fields.put("Адрес", "address");
        fields.put("Заведующий клиникой", "head_of_clinic");
        field_to_find = new JComboBox<>(fields.keySet().toArray(new String[0]));
        field_to_find.setSelectedIndex(0);
        JLabel lblFIO = new JLabel("Поле для поиска:");
        lblFIO.setHorizontalAlignment(SwingConstants.RIGHT);
        lblFIO.setBounds(new Rectangle(PAD, 0 * H_B + PAD, W_L, H_B));
        add(lblFIO);
        field_to_find.setBounds(new Rectangle(W_L + 2 * PAD, 0 * H_B + PAD,
                W_T, H_B));
        field_to_find.setBorder(BorderFactory.createEtchedBorder());
        add(field_to_find);
        JLabel lblADR = new JLabel("Текст:");
        lblADR.setHorizontalAlignment(SwingConstants.RIGHT);
        lblADR.setBounds(new Rectangle(PAD, 1 * H_B + PAD, W_L, H_B));
        add(lblADR);
        search.setBounds(new Rectangle(W_L + 2 * PAD, 1 * H_B + PAD,
                W_T, H_B));
        search.setBorder(BorderFactory.createEtchedBorder());
        add(search);
    }
    public void buildButtons() {
        JButton btnSave = new JButton("Поиск");
        btnSave.setActionCommand(SAVE);
        btnSave.addActionListener(this);
        btnSave.setBounds(new Rectangle(PAD, 2 * H_B + PAD, W_B, H_B));
        add(btnSave);
        JButton btnCancel = new JButton("Закрыть");
        btnCancel.setActionCommand(CANCEL);
        btnCancel.addActionListener(this);
        btnCancel.setBounds(new Rectangle(W_B + 2 * PAD, 2 * H_B + PAD, W_B,
                H_B));
        add(btnCancel);
    }
    public boolean isSave() {return save;}
    public String[] getFieldAndText(){
        return new String[]{fields.get(field_to_find.getSelectedItem()), search.getText()};

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        save = SAVE.equals(action);
        setVisible(false);
    }
}
