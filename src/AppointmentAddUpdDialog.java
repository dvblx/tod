import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class AppointmentAddUpdDialog extends JDialog implements ActionListener {
    private AppointmentFunctions appointmentFunctions = new AppointmentFunctions();
    private static final String SAVE = "Сохранить";
    private static final String CANCEL = "Закрыть";
    private static final int PAD = 10;
    private static final int W_L = 100;
    private static final int W_T = 300;
    private static final int W_B = 120;
    private static final int H_B = 25;
    private final DentistryFunctions dentistryFunctions = new DentistryFunctions();
    private final DentistFunctions dentistFunctions = new DentistFunctions();
    private final TimetableFunctions timetableFunctions = new TimetableFunctions();
    private HashMap<Integer, String> weekHashMap = timetableFunctions.get_week();
    private JComboBox<String> clinic_select;
    JLabel lblSpec = new JLabel("Специализация:");
    JLabel lblDoc = new JLabel("Врач:");
    JLabel lblDay = new JLabel("Дата приёма:");
    private JComboBox<String> type_select = new JComboBox<>(new String[]{"-"});
    private JComboBox<String> doc_select = new JComboBox<>(new String[]{"-"});
    private JComboBox<String> day_select = new JComboBox<>(new String[]{"-"});
    private String clinic_name;
    private String type_name;
    private JButton btnSave = new JButton("Сохранить");
    private JButton btnCancel = new JButton("Закрыть");
    private boolean save = false;
    private int appoontmentId = 0;

    public AppointmentAddUpdDialog(){this(null);}
    public AppointmentAddUpdDialog(Entities.Appointments appointments){
        setLayout(null);
        //buildButtons();
        buildFields();
        // initFields(appointments);
        setModal(true);
        setResizable(false);
        setBounds(300, 300, 450, 150);
        setVisible(true);

    }
    public void buildButtons() {
        btnSave.setActionCommand(SAVE);
        btnSave.addActionListener(this);
        btnSave.setBounds(new Rectangle(PAD, 8 * H_B + PAD, W_B, H_B));
        btnSave.setVisible(true);
        add(btnSave);
        btnCancel.setActionCommand(CANCEL);
        btnCancel.addActionListener(this);
        btnCancel.setBounds(new Rectangle(W_B + 2 * PAD, 8 * H_B + PAD, W_B,
                H_B));
        btnCancel.setVisible(true);
        add(btnCancel);
    }
    public void hideButtons(){
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
    }
    public void buildFields() {
        JLabel cSelect = new JLabel("Выбор клиники");
        cSelect.setHorizontalAlignment(SwingConstants.LEFT);
        cSelect.setBounds(new Rectangle(PAD, 0 * H_B + PAD, W_L, H_B));
        add(cSelect);
        List<Entities.Dentistry> dentistryList = dentistryFunctions.get_all_dentistry();
        HashMap<String, Integer> clinic_dict = new HashMap<>();
        String[] clinic_names = new String[dentistryList.size()+1];
        clinic_names[0] = "-";
        for (int i = 1; i<=dentistryList.size(); i++){
            clinic_names[i] = dentistryList.get(i-1).getName();
            clinic_dict.put(dentistryList.get(i-1).getName(), dentistryList.get(i-1).getDentistry_id());
        }
        clinic_select = new JComboBox<>(clinic_names);
        clinic_select.setBounds(new Rectangle(W_L + 2 * PAD, 0 * H_B + PAD,
                W_T, H_B));
        clinic_select.setBorder(BorderFactory.createEtchedBorder());
        add(clinic_select);
        clinic_select.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (!e.getItem().equals(clinic_names[0])){
                    clinic_name = String.valueOf(e.getItem());
                    setBounds(300, 300, 450, 200);
                    List<String> dentist_types = dentistryFunctions.get_types_in_one_clinic(clinic_dict.getOrDefault(clinic_name, 0));
                    String[] types_array = new String[dentist_types.size()+1];
                    types_array[0] = "-";
                    int i = 1;
                    for(String s: dentist_types){
                        types_array[i] = s;
                        i++;
                    }
                    type_select = new JComboBox<>(types_array);
                    lblSpec.setHorizontalAlignment(SwingConstants.LEFT);
                    lblSpec.setBounds(new Rectangle(PAD, 2 * H_B + PAD, W_L, H_B));
                    add(lblSpec);
                    lblSpec.setVisible(true);
                    type_select.setBounds(new Rectangle(W_L + 2 * PAD, 2 * H_B + PAD, W_T,
                            H_B));
                    type_select.setBorder(BorderFactory.createEtchedBorder());
                    add(type_select);
                    type_select.setVisible(true);
                    type_select.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            if (!e.getItem().equals(types_array[0])){
                                type_name = String.valueOf(e.getItem());
                                setBounds(300, 300, 450, 250);
                                List<Entities.Dentist> dentists = dentistFunctions.filter_by_clinic(clinic_name);
                                String[] dentist_arr = new String[dentists.size()+1];
                                HashMap<String, Integer> doc_dict = new HashMap<>();
                                dentist_arr[0] = "-";
                                int i = 1;
                                for (Entities.Dentist d: dentists){
                                    dentist_arr[i] = d.getDentist_name();
                                    doc_dict.put(d.getDentist_name(), d.getDentist_id());
                                }
                                lblDoc.setHorizontalAlignment(SwingConstants.LEFT);
                                lblDoc.setBounds(new Rectangle(PAD, 4 * H_B + PAD, W_L, H_B));
                                add(lblDoc);
                                lblDoc.setVisible(true);
                                doc_select = new JComboBox<>(dentist_arr);
                                doc_select.setBounds(new Rectangle(W_L + 2 * PAD, 4 * H_B + PAD, W_T,
                                        H_B));
                                doc_select.setBorder(BorderFactory.createEtchedBorder());
                                add(doc_select);
                                day_select.setVisible(true);
                                doc_select.addItemListener(new ItemListener() {
                                    @Override
                                    public void itemStateChanged(ItemEvent e) {
                                        if (!e.getItem().equals(dentist_arr[0])){
                                            setBounds(300, 300, 450, 300);
                                            HashMap<Integer, String> doc_tt = timetableFunctions.get_one_dentist_timetable(doc_dict.getOrDefault(doc_select.getSelectedItem(),0));
                                            String[] days_arr = new String[(doc_tt.size()*5)+1];
                                            List<String> days_list = new ArrayList<>();
                                            days_list.add("-");
                                            String date;
                                            days_arr[0] = "-";
                                            LocalDate localDate = LocalDate.now();
                                            DayOfWeek today = localDate.getDayOfWeek();
                                            int today_number = today.getValue();
                                            int k = 1;
                                            for (int i = 0; i < 5; i++){
                                                for (Integer day_num: doc_tt.keySet()){
                                                    int difference;
                                                    if (day_num < today_number){
                                                        difference = 7 * (i + 1) - (today_number - day_num);
                                                    }
                                                    else{
                                                        difference = (day_num - today_number) + 7 * i;
                                                    }
                                                    date = String.valueOf(localDate.plusDays(difference));
                                                    if (appointmentFunctions.checkAppointment((String) doc_select.getSelectedItem(),
                                                            date, "8:00:00")){
                                                        days_list.add(date);
                                                        k++;
                                                    }
                                                    //days_arr[k] = date;
                                                    k++;
                                                }
                                            }
                                            lblDay.setHorizontalAlignment(SwingConstants.LEFT);
                                            lblDay.setBounds(new Rectangle(PAD, 6 * H_B + PAD, W_L, H_B));
                                            add(lblDay);
                                            lblDay.setVisible(true);
                                            String[] days_array = days_list.toArray(new String[0]);
                                            Arrays.sort(days_array);
                                            day_select = new JComboBox<>(days_array);
                                            day_select.setBounds(new Rectangle(W_L + 2 * PAD, 6 * H_B + PAD, W_T,
                                                    H_B));
                                            day_select.setBorder(BorderFactory.createEtchedBorder());
                                            add(day_select);
                                            day_select.setVisible(true);
                                            day_select.addItemListener(new ItemListener() {
                                                @Override
                                                public void itemStateChanged(ItemEvent e) {
                                                    if(!e.getItem().equals(days_arr[0])){ buildButtons(); }
                                                    else{ hideButtons(); }
                                                }
                                            });
                                        }
                                        else{
                                            lblDay.setVisible(false);
                                            day_select.setVisible(false);
                                            hideButtons();
                                            setBounds(300, 300, 450, 250);
                                        }
                                    }
                                });
                            }
                            else{
                                lblDay.setVisible(false);
                                lblDoc.setVisible(false);
                                day_select.setVisible(false);
                                doc_select.setVisible(false);
                                hideButtons();
                                setBounds(300, 300, 450, 200);
                            }
                        }
                    });
                }
                else{
                    lblDay.setVisible(false);
                    lblDoc.setVisible(false);
                    lblSpec.setVisible(false);
                    day_select.setVisible(false);
                    doc_select.setVisible(false);
                    type_select.setVisible(false);
                    hideButtons();
                    setBounds(300, 300, 450, 150);
                }
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals(SAVE)){
            Entities.Appointments appointment_to_check = getAppointment();
            boolean check = appointmentFunctions.checkAppointment(appointment_to_check.getDentist(), appointment_to_check.getAppointment_day(),
                    appointment_to_check.getAppointment_time());
            if (check){
                save = true;
            }
            else{
                save = false;
                JOptionPane.showMessageDialog(this, "Дата занята");
            }
        }
        setVisible(false);
    }
    public boolean isSave() {
        return save;
    }
    public Entities.Appointments getAppointment(){
        return new Entities.Appointments(appoontmentId, (String) clinic_select.getSelectedItem(), (String) doc_select.getSelectedItem(),
                (String) day_select.getSelectedItem(), "8:00:00");
    }
    //LocalDate localDate = LocalDate.now();
    //localDate = localDate.minusDays(1);
    //DayOfWeek day = localDate.getDayOfWeek();
    //calendar.add(Calendar.DAY_OF_MONTH, 5);
    //System.out.println(localDate);
    //System.out.println(day.getValue());


}
