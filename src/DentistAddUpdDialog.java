import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DentistAddUpdDialog extends  JDialog implements ActionListener {
    private static final String SAVE = "Сохранить";
    private static final String CANCEL = "Закрыть";
    private static final int PAD = 10;
    private static final int W_L = 100;
    private static final int W_T = 300;
    private static final int W_B = 120;
    private static final int H_B = 25;
    private final JTextPane txtFIO = new JTextPane();
    private final JTextPane txtExperience = new JTextPane();
    private final JTextPane txtPhone = new JTextPane();
    private final JTextPane txtHeadOfClinic = new JTextPane();
    private final JTextPane txtFoundationYear = new JTextPane();
    private final JTextPane txtCustomersCount = new JTextPane();
    private final DentistryFunctions dentistryFunctions = new DentistryFunctions();
    private final DentistFunctions dentistFunctions = new DentistFunctions();
    private JComboBox<String> clinic_select;
    private JComboBox<String> type_select;

    private int dentistId = 0;

    private boolean save = false;

    public DentistAddUpdDialog() {
        this(null);
    }

    public DentistAddUpdDialog(Entities.Dentistry dentistry) {
        setLayout(null);
        buildFields();
        initFields(dentistry);
        buildButtons();
        setModal(true);
        setResizable(false);
        setBounds(300, 300, 450, 200);
        setVisible(true);
    }

    public void buildFields() {
        JLabel lblFIO = new JLabel("ФИО:");
        lblFIO.setHorizontalAlignment(SwingConstants.RIGHT);
        lblFIO.setBounds(new Rectangle(PAD, 0 * H_B + PAD, W_L, H_B));
        add(lblFIO);
        txtFIO.setBounds(new Rectangle(W_L + 2 * PAD, 0 * H_B + PAD,
                W_T, H_B));
        txtFIO.setBorder(BorderFactory.createEtchedBorder());
        add(txtFIO);
        List<Entities.Dentistry> dentistryList = dentistryFunctions.get_dentistry();
        String[] clinic_names = new String[dentistryList.size()];
        for (int i = 0; i<dentistryList.size(); i++){
            clinic_names[i] = dentistryList.get(i).getName();
        }
        clinic_select = new JComboBox<>(clinic_names);
        JLabel lblADR = new JLabel("Клиника:");
        lblADR.setHorizontalAlignment(SwingConstants.RIGHT);
        lblADR.setBounds(new Rectangle(PAD, 1 * H_B + PAD, W_L, H_B));
        add(lblADR);
        clinic_select.setBounds(new Rectangle(W_L + 2 * PAD, 1 * H_B + PAD,
                W_T, H_B));
        clinic_select.setBorder(BorderFactory.createEtchedBorder());
        add(clinic_select);
        JLabel lblPHN = new JLabel("Стаж:");
        lblPHN.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPHN.setBounds(new Rectangle(PAD, 2 * H_B + PAD, W_L, H_B));
        add(lblPHN);
        txtExperience.setBounds(new Rectangle(W_L + 2 * PAD, 2 * H_B + PAD,
                W_T, H_B));
        txtExperience.setBorder(BorderFactory.createEtchedBorder());
        add(txtExperience);
        HashMap<String, Integer> dentist_types = dentistFunctions.get_types();
        Set<String> types = dentist_types.keySet();
        String[] types_array = new String[types.size()];
        int i = 0;
        for(String s: types){
            types_array[i] = s;
            i++;
        }
        type_select = new JComboBox<>(types_array);
        JLabel lblHOC = new JLabel("Специализация:");
        lblHOC.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHOC.setBounds(new Rectangle(PAD, 3 * H_B + PAD, W_L, H_B));
        add(lblHOC);
        type_select.setBounds(new Rectangle(W_L + 2 * PAD, 3 * H_B + PAD, W_T,
                H_B));
        type_select.setBorder(BorderFactory.createEtchedBorder());
        add(type_select);

    }
    public void initFields(Entities.Dentistry dentistry) {
        if (dentistry != null) {
            dentistId = dentistry.getDentistry_id();
            txtFIO.setText(dentistry.getName());
            txtExperience.setText(dentistry.getAddress());
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
        btnSave.setBounds(new Rectangle(PAD, 5 * H_B + PAD, W_B, H_B));
        add(btnSave);
        JButton btnCancel = new JButton("Закрыть");
        btnCancel.setActionCommand(CANCEL);
        btnCancel.addActionListener(this);
        btnCancel.setBounds(new Rectangle(W_B + 2 * PAD, 5 * H_B + PAD, W_B,
                H_B));
        add(btnCancel);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        save = SAVE.equals(action);
        setVisible(false);
    }

    public boolean isSave() {
        return save;
    }

    public Entities.Dentist getDentistry() {
        try {
            return new Entities.Dentist(dentistId, txtFIO.getText(),
                    Objects.requireNonNull(clinic_select.getSelectedItem()).toString(),
                    Integer.parseInt(txtExperience.getText()),
                    Objects.requireNonNull(type_select.getSelectedItem()).toString());
        } catch (NumberFormatException e) {
            return new Entities.Dentist(dentistId, txtFIO.getText(),
                    Objects.requireNonNull(clinic_select.getSelectedItem()).toString(),
                    0, Objects.requireNonNull(type_select.getSelectedItem()).toString());
        }

    }
}
