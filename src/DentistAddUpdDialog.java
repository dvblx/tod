import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

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
    private final DentistryFunctions dentistryFunctions = new DentistryFunctions();
    private final DentistFunctions dentistFunctions = new DentistFunctions();
    private final TimetableFunctions timetableFunctions = new TimetableFunctions();
    private JComboBox<String> clinic_select;
    private JComboBox<String> type_select;
    private HashMap<Integer, String> weekHashMap = timetableFunctions.get_week();
    private JCheckBox[] days = new JCheckBox[weekHashMap.size()];
    private JTextPane[] time = new JTextPane[weekHashMap.size()];


    private int dentistId = 0;

    private boolean save = false;

    public DentistAddUpdDialog() {
        this(null);
    }

    public DentistAddUpdDialog(Entities.Dentist dentist) {
        setLayout(null);
        buildFields();
        initFields(dentist);
        buildButtons();
        setModal(true);
        setResizable(false);
        setBounds(300, 300, 450, 400);
        setVisible(true);
    }

    public void buildFields() {
        JLabel lblFIO = new JLabel("ФИО:");
        lblFIO.setHorizontalAlignment(SwingConstants.LEFT);
        lblFIO.setBounds(new Rectangle(PAD, 0 * H_B + PAD, W_L, H_B));
        add(lblFIO);
        txtFIO.setBounds(new Rectangle(W_L + 2 * PAD, 0 * H_B + PAD,
                W_T, H_B));
        txtFIO.setBorder(BorderFactory.createEtchedBorder());
        add(txtFIO);
        List<Entities.Dentistry> dentistryList = dentistryFunctions.get_all_dentistry();
        String[] clinic_names = new String[dentistryList.size()];
        for (int i = 0; i<dentistryList.size(); i++){
            clinic_names[i] = dentistryList.get(i).getName();
        }
        clinic_select = new JComboBox<>(clinic_names);
        JLabel lblADR = new JLabel("Клиника:");
        lblADR.setHorizontalAlignment(SwingConstants.LEFT);
        lblADR.setBounds(new Rectangle(PAD, 1 * H_B + PAD, W_L, H_B));
        add(lblADR);
        clinic_select.setBounds(new Rectangle(W_L + 2 * PAD, 1 * H_B + PAD,
                W_T, H_B));
        clinic_select.setBorder(BorderFactory.createEtchedBorder());
        add(clinic_select);
        JLabel lblPHN = new JLabel("Стаж:");
        lblPHN.setHorizontalAlignment(SwingConstants.LEFT);
        lblPHN.setBounds(new Rectangle(PAD, 2 * H_B + PAD, W_L, H_B));
        add(lblPHN);
        txtExperience.setBounds(new Rectangle(W_L + 2 * PAD, 2 * H_B + PAD,
                W_T, H_B));
        txtExperience.setBorder(BorderFactory.createEtchedBorder());
        add(txtExperience);
        // ПЕРЕДЕЛАТЬ
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
        lblHOC.setHorizontalAlignment(SwingConstants.LEFT);
        lblHOC.setBounds(new Rectangle(PAD, 3 * H_B + PAD, W_L, H_B));
        add(lblHOC);
        type_select.setBounds(new Rectangle(W_L + 2 * PAD, 3 * H_B + PAD, W_T,
                H_B));
        type_select.setBorder(BorderFactory.createEtchedBorder());
        add(type_select);

        JLabel info = new JLabel("Расписание: (день - часы приёма)");
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setBounds(new Rectangle(PAD, 4 * H_B + PAD, 450, H_B));
        add(info);
        int y = 5;
        int n = 0;
        for (int k = 1; k <= weekHashMap.size(); k++){
            days[n] = new JCheckBox(weekHashMap.get(k));
            days[n].setHorizontalAlignment(SwingConstants.LEFT);
            days[n].setBounds(new Rectangle(PAD, y * H_B + PAD, W_L+20, H_B));

            time[n] = new JTextPane();
            time[n].setBounds(new Rectangle(W_L + 2 * PAD, y * H_B + PAD,
                    W_T-20, H_B));
            time[n].setBorder(BorderFactory.createEtchedBorder());
            time[n].setVisible(false);
            int finalN = n;
            days[n].addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    time[finalN].setVisible(e.getStateChange() == ItemEvent.SELECTED);
                }
            });
            add(time[n]);
            add(days[n]);
            n++;
            y++;
        }

    }
    public void initFields(Entities.Dentist dentist) {
        if (dentist != null) {
            dentistId = dentist.getDentist_id();
            txtFIO.setText(dentist.getDentist_name());
            List<Entities.Dentistry> dentistryList = dentistryFunctions.get_all_dentistry();
            for (int i = 0; i< dentistryList.size(); i++){
                if (dentistryList.get(i).getName().equals(dentist.getDentistry())){
                    clinic_select.setSelectedIndex(i);
                    break;
                }
            }
            txtExperience.setText(String.valueOf(dentist.getExperience()));
            HashMap<String, Integer> dentist_types = dentistFunctions.get_types();
            type_select.setSelectedIndex(dentist_types.getOrDefault(dentist.getDentist_type(), 0)-2);
            HashMap<Integer, String> doc_tt = timetableFunctions.get_one_dentist_timetable(dentistId);
            for (int k = 1; k <= weekHashMap.size(); k++){
                for (Integer day: doc_tt.keySet()){
                    if (k == day){
                        days[k-1].setSelected(true);
                        time[k-1].setText(doc_tt.get(k));
                        break;
                    }
                }
            }

        }
    }
    public void buildButtons() {
        JButton btnSave = new JButton("Сохранить");
        btnSave.setActionCommand(SAVE);
        btnSave.addActionListener(this);
        btnSave.setBounds(new Rectangle(PAD, 12 * H_B + PAD, W_B, H_B));
        add(btnSave);
        JButton btnCancel = new JButton("Закрыть");
        btnCancel.setActionCommand(CANCEL);
        btnCancel.addActionListener(this);
        btnCancel.setBounds(new Rectangle(W_B + 2 * PAD, 12 * H_B + PAD, W_B,
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

    public Entities.Dentist getDentist() {
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
    public List<Entities.TimeTable> getEntry(){
        List<Entities.TimeTable> entries = new ArrayList<>();
        for (int i = 0; i<days.length; i++){
            timetableFunctions.deleteEntry(dentistId, i+1);
            if(days[i].isSelected()){
                entries.add(new Entities.TimeTable(dentistId, txtFIO.getText(),
                        Objects.requireNonNull(clinic_select.getSelectedItem()).toString(),
                        weekHashMap.get(i+1), time[i].getText()));
            }
        }
        return entries;
    }
}
