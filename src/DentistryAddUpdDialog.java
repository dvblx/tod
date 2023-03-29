import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DentistryAddUpdDialog extends JDialog implements ActionListener {
    // Заголовки кнопок
    private static final String APPOINTMENT = "APPOINTMENT";
    private static final String DENTISTRY = "DENTISTRY";
    private static final String DENTIST = "DENTIST";
    private static final String SAVE = "Сохранить";
    private static final String CANCEL = "Закрыть";
    private static final int PAD = 10;
    private static final int W_L = 100;
    private static final int W_T = 300;
    private static final int W_B = 120;
    private static final int H_B = 25;
    private final JTextPane txtFIO = new JTextPane();
    private final JTextPane txtAddress = new JTextPane();
    private final JTextPane txtPhone = new JTextPane();
    private final JTextPane txtHeadOfClinic = new JTextPane();
    private final JTextPane txtFoundationYear = new JTextPane();
    private final JTextPane txtCustomersCount = new JTextPane();
    private int dentistryId = 0;

    private boolean save = false;

    public DentistryAddUpdDialog() {
        this(null);
    }

    public DentistryAddUpdDialog(Entities.Dentistry dentistry) {
        setLayout(null);
        buildFields();
        initFields(dentistry);
        buildButtons();
        setModal(true);
        setResizable(false);
        setBounds(300, 300, 450, 220);
        setVisible(true);
    }

    public void buildFields() {
        JLabel lblFIO = new JLabel("Название:");
        lblFIO.setHorizontalAlignment(SwingConstants.RIGHT);
        lblFIO.setBounds(new Rectangle(PAD, 0 * H_B + PAD, W_L, H_B));
        add(lblFIO);
        txtFIO.setBounds(new Rectangle(W_L + 2 * PAD, 0 * H_B + PAD,
                W_T, H_B));
        txtFIO.setBorder(BorderFactory.createEtchedBorder());
        add(txtFIO);
        JLabel lblADR = new JLabel("Адрес:");
        lblADR.setHorizontalAlignment(SwingConstants.RIGHT);
        lblADR.setBounds(new Rectangle(PAD, 1 * H_B + PAD, W_L, H_B));
        add(lblADR);
        txtAddress.setBounds(new Rectangle(W_L + 2 * PAD, 1 * H_B + PAD,
                W_T, H_B));
        txtAddress.setBorder(BorderFactory.createEtchedBorder());
        add(txtAddress);
        JLabel lblPHN = new JLabel("Телефон:");
        lblPHN.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPHN.setBounds(new Rectangle(PAD, 2 * H_B + PAD, W_L, H_B));
        add(lblPHN);
        txtPhone.setBounds(new Rectangle(W_L + 2 * PAD, 2 * H_B + PAD,
                W_T, H_B));
        txtPhone.setBorder(BorderFactory.createEtchedBorder());
        add(txtPhone);
        JLabel lblHOC = new JLabel("Заведующий клиникой:");
        lblHOC.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHOC.setBounds(new Rectangle(PAD, 3 * H_B + PAD, W_L, H_B));
        add(lblHOC);
        txtHeadOfClinic.setBounds(new Rectangle(W_L + 2 * PAD, 3 * H_B + PAD, W_T,
                H_B));
        txtHeadOfClinic.setBorder(BorderFactory.createEtchedBorder());
        add(txtHeadOfClinic);
        // Набор метки и поля для Email
        JLabel lblFY = new JLabel("Год основания:");
        lblFY.setHorizontalAlignment(SwingConstants.RIGHT);
        lblFY.setBounds(new Rectangle(PAD, 4 * H_B + PAD, W_L, H_B));
        add(lblFY);
        txtFoundationYear.setBounds(new Rectangle(W_L + 2 * PAD, 4 * H_B + PAD, W_T,
                H_B));
        txtFoundationYear.setBorder(BorderFactory.createEtchedBorder());
        add(txtFoundationYear);
        JLabel lblCC = new JLabel("Количество клиентов:");
        lblCC.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCC.setBounds(new Rectangle(PAD, 5 * H_B + PAD, W_L, H_B));
        add(lblCC);
        txtCustomersCount.setBounds(new Rectangle(W_L + 2 * PAD, 5 * H_B + PAD, W_T,
                H_B));
        txtCustomersCount.setBorder(BorderFactory.createEtchedBorder());
        add(txtCustomersCount);
    }
    public void initFields(Entities.Dentistry dentistry) {
        if (dentistry != null) {
            dentistryId = dentistry.getDentistry_id();
            txtFIO.setText(dentistry.getName());
            txtAddress.setText(dentistry.getAddress());
            txtPhone.setText(dentistry.getPhone());
            txtHeadOfClinic.setText(dentistry.getHead_of_clinic());
            txtFoundationYear.setText(String.valueOf(dentistry.getFoundation_year()));
            txtCustomersCount.setText(String.valueOf(dentistry.getCustomer_count()));
        }
    }
    public void buildButtons() {
        JButton btnSave = new JButton("Сохранить");
        btnSave.setActionCommand(SAVE);
        btnSave.addActionListener(this);
        btnSave.setBounds(new Rectangle(PAD, 6 * H_B + PAD, W_B, H_B));
        add(btnSave);
        JButton btnCancel = new JButton("Закрыть");
        btnCancel.setActionCommand(CANCEL);
        btnCancel.addActionListener(this);
        btnCancel.setBounds(new Rectangle(W_B + 2 * PAD, 6 * H_B + PAD, W_B,
                H_B));
        add(btnCancel);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals(SAVE)){
            if (Validation()){
                save = true;
                setVisible(false);
            }
        }
    }

    public boolean isSave() {
        return save;
    }

    public Entities.Dentistry getDentistry() {
        try {
            return new Entities.Dentistry(dentistryId, txtFIO.getText(), txtPhone.getText(), txtHeadOfClinic.getText(),
                    txtAddress.getText(), Integer.parseInt(txtFoundationYear.getText()),
                    Integer.parseInt(txtCustomersCount.getText()));
        } catch (NumberFormatException e) {
            return new Entities.Dentistry(dentistryId, txtFIO.getText(), txtPhone.getText(), txtHeadOfClinic.getText(),
                    txtAddress.getText(), 0,
                    0);
        }

    }
    public boolean Validation(){
        if (txtFIO.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Поле ввода ФИО не заполнено");
            return false;
        }
        else if (txtPhone.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Поле ввода номера телефона не заполнено");
            return false;
        }
        else if (txtAddress.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Поле ввода адреса не заполнено");
            return false;
        }
        else if (txtCustomersCount.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Поле ввода количества клиентов не заполнено");
            return false;
        }
        else if (txtFoundationYear.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Поле ввода года основания не заполнено");
            return false;
        }
        else if (txtHeadOfClinic.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Поле ввода управляющего клиникой не заполнено");
            return false;
        }
        else if (txtPhone.getText().length() != 11 || (txtPhone.getText().length() == 11 && txtPhone.getText().charAt(0) != '8'))
        {
            if (!(txtPhone.getText().length() == 12 && (txtPhone.getText().charAt(0) == '+') && (txtPhone.getText().charAt(1) == '7'))){
                JOptionPane.showMessageDialog(this,"Некорректный формат номера телефона\n" +
                        "Пример: 89281830269 или +79281830269");
                return false;
            }

        }
        try {
            Integer.parseInt(txtFoundationYear.getText());
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"В поле ввода года основания нужно ввести число");
            return false;
        }
        try {
            Integer.parseInt(txtCustomersCount.getText());
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"В поле ввода количества клиентов нужно ввести число");
            return false;
        }
        return true;
    }

}
