import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class DentistrySortDialog extends JDialog implements ActionListener {
    private static final HashMap<String, String> fields = new HashMap<String, String>();
    private static final HashMap<String, String> order = new HashMap<String, String>();
    private static final String SAVE = "Сохранить";
    private static final String CANCEL = "Закрыть";
    private static final int PAD = 10;
    private static final int W_L = 100;
    private static final int W_T = 300;
    private static final int W_B = 120;
    private static final int H_B = 25;

    JComboBox<String> field_to_sort, sorting_order;
    private boolean save = false;

    public DentistrySortDialog(){
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
        fields.put("Год основания", "foundation_year");
        fields.put("Количество клиентов", "customer_count");
        order.put("По возрастанию", "ASC");
        order.put("По убыванию", "DESC");
        field_to_sort = new JComboBox<>(fields.keySet().toArray(new String[0]));
        field_to_sort.setSelectedIndex(0);
        JLabel lbl1 = new JLabel("Поле для сортировки:");
        lbl1.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl1.setBounds(new Rectangle(PAD, PAD, W_L, H_B));
        add(lbl1);
        field_to_sort.setBounds(new Rectangle(W_L + 2 * PAD, PAD,
                W_T, H_B));
        field_to_sort.setBorder(BorderFactory.createEtchedBorder());
        add(field_to_sort);
        sorting_order = new JComboBox<>(order.keySet().toArray(new String[0]));
        sorting_order.setSelectedIndex(0);
        JLabel lbl2 = new JLabel("Порядок сортировки:");
        lbl2.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl2.setBounds(new Rectangle(PAD, H_B + PAD, W_L, H_B));
        add(lbl2);
        sorting_order.setBounds(new Rectangle(W_L + 2 * PAD, H_B + PAD,
                W_T, H_B));
        sorting_order.setBorder(BorderFactory.createEtchedBorder());
        add(sorting_order);
    }
    public void buildButtons() {
        JButton btnSave = new JButton("Сортировать");
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
        return new String[]{fields.get(field_to_sort.getSelectedItem()), order.get(sorting_order.getSelectedItem())};

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        save = SAVE.equals(action);
        setVisible(false);
    }
}
