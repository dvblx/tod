import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DentistryFrame extends JFrame implements ActionListener {
    private static final String LOAD = "LOAD";
    private static final String ADD = "ADD";
    private static final String EDIT = "EDIT";
    private static final String DELETE = "DELETE";
    private static final String SORT= "SORT";
    private static final String SEARCH = "SEARCH";
    private static final String APPOINTMENT = "APPOINTMENT";
    private static final String APPOINTMENT_PAST = "APPOINTMENT_PAST";
    private static final String TIMETABLE = "TIMETABLE";
    private static final String DENTISTRY = "DENTISTRY";
    private static final String DENTIST = "DENTIST";
    private static final String REPORT = "REPORT";
    private static final String FILLED = "Заполненные";
    private static final String UNFILLED = "Незаполненные";
    private final JTable dTable = new JTable();
    private final JPanel btnPanel = new JPanel();
    private final GridBagLayout gridbag = new GridBagLayout();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private int category = 1;
    private final DentistFunctions dentistFunctions = new DentistFunctions();
    private final DentistryFunctions dentistryFunctions = new DentistryFunctions();
    private final TimetableFunctions timetableFunctions = new TimetableFunctions();
    private final AppointmentFunctions appointmentFunctions = new AppointmentFunctions();
    private final PreviousAppointmentFunctions previousAppointmentFunctions = new PreviousAppointmentFunctions();
    private final JMenu clinic_select;
    private final JMenu doctor_select;
    private final JMenu appointment_select;
    private boolean filled = true;
    private String selected_clinic_name;

    public DentistryFrame(){
        dTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 0, 5);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Меню");
        JMenuItem pd = new JMenuItem("Клиники");
        pd.setActionCommand(DENTISTRY);
        pd.addActionListener(this);
        menu.add(pd);
        JMenuItem pdt = new JMenuItem("Врачи");
        pdt.setActionCommand(DENTIST);
        pdt.addActionListener(this);
        menu.add(pdt);
        JMenuItem ptt = new JMenuItem("Расписание врачей");
        ptt.setActionCommand(TIMETABLE);
        ptt.addActionListener(this);
        menu.add(ptt);
        JMenuItem fa = new JMenuItem("Предстоящие приёмы");
        fa.setActionCommand(APPOINTMENT);
        fa.addActionListener(this);
        menu.add(fa);
        JMenuItem pa = new JMenuItem("Прошедшие приёмы");
        pa.setActionCommand(APPOINTMENT_PAST);
        pa.addActionListener(this);
        menu.add(pa);
        menuBar.add(menu);
        clinic_select = new JMenu("Выбор клиники");
        menuBar.add(clinic_select);
        clinic_select.setVisible(false);
        doctor_select = new JMenu("Выбор врача");
        menuBar.add(doctor_select);
        doctor_select.setVisible(false);
        setJMenuBar(menuBar);
        appointment_select = new JMenu("Приёмы");
        JMenuItem i1 = new JMenuItem(UNFILLED);
        i1.setActionCommand(UNFILLED);
        i1.addActionListener(this);
        JMenuItem i2 = new JMenuItem(FILLED);
        i2.setActionCommand(FILLED);
        i2.addActionListener(this);
        appointment_select.add(i1);
        appointment_select.add(i2);
        appointment_select.setVisible(false);
        menuBar.add(appointment_select);
        btnPanel.setLayout(gridbag);
        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());
        left.add(btnPanel, BorderLayout.NORTH);
        add(left, BorderLayout.WEST);
        add(new JScrollPane(dTable), BorderLayout.CENTER);
        setBounds(100, 200, 1250, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        previousAppointmentFunctions.migrate_appointments();
        selected_clinic_name = "-";
    }
    private JButton createButton(GridBagLayout gridbag, GridBagConstraints gbc, String title, String action) {
        JButton button = new JButton(title);
        button.setActionCommand(action);
        button.addActionListener(this);
        gridbag.setConstraints(button, gbc);
        return button;
    }
    private void BuildButtons(){
        // 1 - врачи, 2 - клиники, 3 - предстоящие приёмы, 4  - расписание, 5 - прошедшие приёмы
        btnPanel.removeAll();
        switch (category){
            case 1, 3, 5 ->{
                btnPanel.add(createButton(gridbag, gbc, "Обновить", LOAD));
                btnPanel.add(createButton(gridbag, gbc, "Добавить", ADD));
                btnPanel.add(createButton(gridbag, gbc, "Исправить", EDIT));
                btnPanel.add(createButton(gridbag, gbc, "Удалить", DELETE));
                btnPanel.add(createButton(gridbag, gbc, "Сохранить отчёт", REPORT));
            }
            case 2 ->{
                btnPanel.add(createButton(gridbag, gbc, "Обновить", LOAD));
                btnPanel.add(createButton(gridbag, gbc, "Добавить", ADD));
                btnPanel.add(createButton(gridbag, gbc, "Исправить", EDIT));
                btnPanel.add(createButton(gridbag, gbc, "Удалить", DELETE));
                btnPanel.add(createButton(gridbag, gbc, "Сортировать", SORT));
                btnPanel.add(createButton(gridbag, gbc, "Поиск", SEARCH));
                btnPanel.add(createButton(gridbag, gbc, "Сохранить отчёт", REPORT));
            }
            case 4 -> {
                btnPanel.add(createButton(gridbag, gbc, "Обновить", LOAD));
                btnPanel.add(createButton(gridbag, gbc, "Сохранить отчёт", REPORT));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        // В зависимости от команды выполняем действия
        switch (action) {
            case DENTIST -> {
                category = 1;
                loadData(category);
            }
            case DENTISTRY -> {
                category = 2;
                loadData(category);
            }
            case APPOINTMENT -> {
                category = 3;
                loadData(category);
            }
            case TIMETABLE -> {
                category = 4;
                loadData(category);
            }
            case APPOINTMENT_PAST -> {
                category = 5;
                loadData(category);
            }
            case LOAD -> loadData(category);
            case ADD -> addData(category);
            case EDIT -> editData(category);
            case DELETE -> deleteData(category);
            case SORT -> sortData(category);
            case SEARCH -> searchData(category);
            case REPORT -> createReport(category);
            case UNFILLED -> {
                filled = false;
                loadData(category);
            }
            case FILLED ->{
                filled = true;
                loadData(category);
            }
        }
        JMenuItem[] items = clinic_filling();
        List<Entities.Dentistry> dentistries = dentistryFunctions.get_all_dentistry();

        for (int i = 0; i < items.length; i++) {
            //System.out.println(items[i].getActionCommand());
            if (action.equals(items[i].getActionCommand())) {
                getFilteredData(category, dentistries.get(i).getName(), "");
                selected_clinic_name = dentistries.get(i).getName();
                if (category == 5){doctor_select.setVisible(true);}
            }
        }

        if (!selected_clinic_name.equals("-")){
            JMenuItem[] items2 = doctor_filling(selected_clinic_name);
            for (JMenuItem jMenuItem : items2) {
                if (action.equals(jMenuItem.getActionCommand())) {
                    getFilteredData(category, selected_clinic_name, jMenuItem.getActionCommand());
                }
            }
        }
    }
    private void loadData(int number){
        // 1 - врачи, 2 - клиники, 3 - предстоящие приёмы, 4  - расписание, 5 - прошедшие приёмы
        BuildButtons();
        doctor_select.setVisible(false);
        switch (number) {
            case 1 -> {
                clinic_select.setVisible(true);
                appointment_select.setVisible(false);
                clinic_filling();
                List<Entities.Dentist> dentistList = dentistFunctions.get_dentists();
                DentistTable dt = new DentistTable(dentistList);
                dTable.setModel(dt);
            }
            case 2 -> {
                clinic_select.setVisible(false);
                appointment_select.setVisible(false);
                List<Entities.Dentistry> dentistryList = dentistryFunctions.get_all_dentistry();
                DentistryTable dryt = new DentistryTable(dentistryList);
                dTable.setModel(dryt);
            }
            case 3 -> {
                clinic_select.setVisible(true);
                appointment_select.setVisible(false);
                clinic_filling();
                List<Entities.ForthcomingAppointment> appointmentsList = appointmentFunctions.get_appointments();
                AppointmentTable at = new AppointmentTable(appointmentsList);
                dTable.setModel(at);
            }
            case 4 -> {
                clinic_select.setVisible(true);
                appointment_select.setVisible(false);
                clinic_filling();
                List<Entities.TimeTable> timeTableList = timetableFunctions.get_timetable();
                TimeTableTable ttt = new TimeTableTable(timeTableList);
                dTable.setModel(ttt);
             }
            case 5 -> {
                clinic_select.setVisible(true);
                appointment_select.setVisible(true);
                clinic_filling();
                List<Entities.PreviousAppointment> previousAppointmentList;
                if (filled){
                    previousAppointmentList = previousAppointmentFunctions.get_filled_appointments();
                }
                else {
                    previousAppointmentList = previousAppointmentFunctions.get_unfilled_appointments();
                }

                PreviousAppointmentTable pat = new PreviousAppointmentTable(previousAppointmentList);
                dTable.setModel(pat);
            }
        }
    }
    private void getFilteredData(int category, String param1, String param2){
        // 1 - врачи, 2 - клиники, 3 - предстоящие приёмы, 4  - расписание, 5 - прошедшие приёмы
        switch (category){
            case 1 -> {
                List<Entities.Dentist> dentistList = dentistFunctions.filter_by_clinic(param1);
                DentistTable dt = new DentistTable(dentistList);
                dTable.setModel(dt);
            }
            case 3 -> {
                if (param2.equals("")){
                    List<Entities.ForthcomingAppointment> forthcomingAppointmentList =
                            appointmentFunctions.filter_by_clinic(param1);
                    AppointmentTable fat = new AppointmentTable(forthcomingAppointmentList);
                    dTable.setModel(fat);
                }
                else{
                    List<Entities.ForthcomingAppointment> forthcomingAppointmentList =
                            appointmentFunctions.get_one_doctor_appointments(param1, param2);
                    AppointmentTable fat = new AppointmentTable(forthcomingAppointmentList);
                    dTable.setModel(fat);
                }

            }
            case 4 -> {
                List<Entities.TimeTable> timeTableList = timetableFunctions.filter_by_clinic(param1);
                TimeTableTable ttt = new TimeTableTable(timeTableList);
                dTable.setModel(ttt);
            }
            case 5 ->{
                if (param2.equals("")){
                    if (filled){
                        List<Entities.PreviousAppointment> previousAppointmentList =
                                previousAppointmentFunctions.get_filled_appointments_with_filter_by_clinic(param1);
                        PreviousAppointmentTable pat = new PreviousAppointmentTable(previousAppointmentList);
                        dTable.setModel(pat);
                    }
                    else {
                        List<Entities.PreviousAppointment> previousAppointmentList =
                                previousAppointmentFunctions.get_unfilled_appointments_with_filter_by_clinic(param1);
                        PreviousAppointmentTable pat = new PreviousAppointmentTable(previousAppointmentList);
                        dTable.setModel(pat);
                    }
                }
                else{
                    if (filled){
                        List<Entities.PreviousAppointment> previousAppointmentList =
                                previousAppointmentFunctions.get_one_doctor_filled_appointments(param1, param2);
                        PreviousAppointmentTable pat = new PreviousAppointmentTable(previousAppointmentList);
                        dTable.setModel(pat);
                    }
                    else {
                        List<Entities.PreviousAppointment> previousAppointmentList =
                                previousAppointmentFunctions.get_one_doctor_unfilled_appointments(param1, param2);
                        PreviousAppointmentTable pat = new PreviousAppointmentTable(previousAppointmentList);
                        dTable.setModel(pat);
                    }
                }
            }
        }
    }
    private void addData(int category){
        // 1 - врачи, 2 - клиники, 3 - предстоящие приёмы, 4  - расписание, 5 - прошедшие приёмы
        switch (category){
            case 1 ->{
                DentistAddUpdDialog dentistAddUpdDialog = new DentistAddUpdDialog();
                saveDentist(dentistAddUpdDialog);
            }
            case 2->{
                DentistryAddUpdDialog dentistryAddUpdDialog = new DentistryAddUpdDialog();
                saveDentistry(dentistryAddUpdDialog);
            }
            case 3 ->{
                AppointmentAddUpdDialog appointmentAddUpdDialog = new AppointmentAddUpdDialog();
                saveAppointment(appointmentAddUpdDialog);
            }
        }
        loadData(category);
    }
    public void editData(int category){
        // 1 - врачи, 2 - клиники, 3 - предстоящие приёмы, 4  - расписание, 5 - прошедшие приёмы
        int sr = dTable.getSelectedRow();
        if (sr != -1) {
            switch (category){
                case 1 -> {
                    int id = Integer.parseInt(dTable.getModel().getValueAt(sr, 0).toString());
                    DentistAddUpdDialog dentistAddUpdDialog =
                            new DentistAddUpdDialog(dentistFunctions.get_one_dentist(id));
                    saveDentist(dentistAddUpdDialog);

                }
                case 2 -> {
                    int id = Integer.parseInt(dTable.getModel().getValueAt(sr, 0).toString());
                    DentistryAddUpdDialog dentistryAddUpdDialog =
                            new DentistryAddUpdDialog(dentistryFunctions.get_one_dentistry(id));
                    saveDentistry(dentistryAddUpdDialog);
                }
                case 3 -> {
                    System.out.println("3");
                }
                case 5 -> {
                    if (!filled){
                        int id = Integer.parseInt(dTable.getModel().getValueAt(sr, 0).toString());
                        Entities.PreviousAppointment pa = previousAppointmentFunctions.get_one_appointment(id);
                        PreviousAppointmentAddUpdDialog previousAppointmentAddUpdDialog =
                                new PreviousAppointmentAddUpdDialog(pa);
                        saveAppointment(previousAppointmentAddUpdDialog, pa);
                    }
                }
                default -> {
                    System.out.println("4");
                }
            }
            loadData(category);

        } else {
            JOptionPane.showMessageDialog(this, "Вы должны выделить строку для редактирования");
        }

    }
    public void deleteData(int category){
        switch (category){
            //case 1 -> deleteDentist();
            case 2 -> deleteDentistry();
            //case 3 -> deleteAppointment();
        }
    }
    public void sortData(int category){
        switch (category){
            //case 1 -> sortDentist();
            case 2 -> {
                DentistrySortDialog dentistrySortDialog = new DentistrySortDialog();
                sortDentistry(dentistrySortDialog);
            }
            //case 3 -> sortAppointment();
        }
    }
    public void searchData(int category){
        switch (category){
            //case 1 -> sortDentist();
            case 2 -> {
                DentistrySearchDialog dentistrySearchDialog = new DentistrySearchDialog();
                searchDentistry(dentistrySearchDialog);
            }
            //case 3 -> sortAppointment();
        }
    }
    public void createReport(int category){
        // 1 - врачи, 2 - клиники, 3 - приёмы, 4  - расписание
        String name_of_report = "-";
        switch (category){
            case 1 -> {
                List<Entities.Dentist> dentists = new ArrayList<>();
                for (int i = 0; i < dTable.getRowCount(); i++){
                    dentists.add(new Entities.Dentist((Integer) dTable.getValueAt(i, 0),
                            (String) dTable.getValueAt(i, 1),
                            (String) dTable.getValueAt(i, 2), (Integer) dTable.getValueAt(i, 3),
                            (String) dTable.getValueAt(i, 4)));

                }
                name_of_report = dentistFunctions.createReport(dentists);
            }
            case 2 -> {
                List<Entities.Dentistry> dentistryList = new ArrayList<>();
                for (int i = 0; i < dTable.getRowCount(); i++){
                    dentistryList.add(new Entities.Dentistry((Integer) dTable.getValueAt(i, 0),
                            (String) dTable.getValueAt(i, 1),
                            (String) dTable.getValueAt(i, 2),
                            (String) dTable.getValueAt(i, 3), (String) dTable.getValueAt(i, 4),
                            (Integer) dTable.getValueAt(i, 5), (Integer) dTable.getValueAt(i, 6)));
                }
                name_of_report = dentistryFunctions.createReport(dentistryList);
            }
            case 3 -> {
                List<Entities.ForthcomingAppointment> appointmentsList = new ArrayList<>();
                for (int i = 0; i< dTable.getRowCount(); i++){
                    appointmentsList.add(new Entities.ForthcomingAppointment((Integer) dTable.getValueAt(i, 0),
                            (String) dTable.getValueAt(i, 1), (String) dTable.getValueAt(i, 2),
                            (String) dTable.getValueAt(i, 3), (String) dTable.getValueAt(i, 4),
                            (String) dTable.getValueAt(i, 5)));

                }
                name_of_report = appointmentFunctions.createReport(appointmentsList);
            }
            case 4 -> {
                List<Entities.TimeTable> timeTableList = new ArrayList<>();
                for (int i =0; i < dTable.getRowCount(); i++){
                    timeTableList.add(new Entities.TimeTable((Integer) dTable.getValueAt(i, 0),
                            (String) dTable.getValueAt(i, 1),
                            (String) dTable.getValueAt(i, 2), (String) dTable.getValueAt(i, 3),
                            (String) dTable.getValueAt(i, 4)));
                }
                name_of_report = timetableFunctions.createReport(timeTableList);
            }

        }
        if (!name_of_report.equals("-")){
            JOptionPane.showMessageDialog(this, "Отчёт " + name_of_report + " успешно создан");
        }
        else{
            JOptionPane.showMessageDialog(this, "Произошла ошибка при создании отчёта");
        }
    }
    private void saveDentistry(DentistryAddUpdDialog d){
        if (d.isSave()){
            Entities.Dentistry dentistry = d.getDentistry();
            if (dentistry.getDentistry_id() != 0){
                dentistryFunctions.updateDentistry(dentistry);
            }
            else{
                dentistryFunctions.addDentistry(dentistry);
            }
            //loadData(category);
        }
    }
    private void saveDentist(DentistAddUpdDialog d){
        if (d.isSave()){
            Entities.Dentist dentist= d.getDentist();
            List<Entities.TimeTable> entries = d.getEntry();
            if (dentist.getDentist_id() != 0){ dentistFunctions.updateDentist(dentist); }
            else{ dentistFunctions.addDentist(dentist); }
            for (Entities.TimeTable entry : entries) { timetableFunctions.addEntry(entry); }
            //loadData(category);
        }
    }
    private void saveAppointment(AppointmentAddUpdDialog d){
        if (d.isSave()){
            Entities.ForthcomingAppointment appointment = d.getAppointment();
            if (appointment.getId() != 0){
                System.out.println("upd");
            }
            else{ appointmentFunctions.addAppointment(appointment); }
        }
    }
    private void saveAppointment(PreviousAppointmentAddUpdDialog d, Entities.PreviousAppointment pa){
        if (d.isSave()){
            Entities.PreviousAppointment appointment = d.getAppointment(pa);
            previousAppointmentFunctions.updateAppointment(appointment);
        }
        else{
            previousAppointmentFunctions.deleteAppointment(pa.getPrevious_appointment_id());
        }
    }
    private void deleteDentistry(){
        int sr = dTable.getSelectedRow();
        if (sr != -1) {
            // Получаем ID контакта
            int id = Integer.parseInt(dTable.getModel().getValueAt(sr,
                    0).toString());
            // Удаляем контакт
            dentistryFunctions.deleteDentistry(id);
            // перегружаем список контактов
            loadData(category);
        } else {
            JOptionPane.showMessageDialog(this, "Вы должны выделить строку для удаления");
        }
    }
    private void searchDentistry(DentistrySearchDialog d){
        if (d.isSave()){
            List<Entities.Dentistry> dentistryList = dentistryFunctions.searchDentistry(d.getFieldAndText()[0],
                    d.getFieldAndText()[1]);
            DentistryTable dryt = new DentistryTable(dentistryList);
            dTable.setModel(dryt);
        }
    }
    private void sortDentistry(DentistrySortDialog d){
        if (d.isSave()){
            List<Entities.Dentistry> dentistryList = dentistryFunctions.sortDentistry(d.getFieldAndText()[0],
                    d.getFieldAndText()[1]);
            DentistryTable dentistryTable = new DentistryTable(dentistryList);
            dTable.setModel(dentistryTable);
        }
    }
    public JMenuItem[] clinic_filling(){
        clinic_select.removeAll();
        List<Entities.Dentistry> dentistryList = dentistryFunctions.get_all_dentistry();
        JMenuItem[] clinic_array = new JMenuItem[dentistryList.size()];
        for (int i = 0; i < dentistryList.size(); i++){
            clinic_array[i] = new JMenuItem(dentistryList.get(i).getName());
            clinic_array[i].setActionCommand(dentistryList.get(i).getName());
            clinic_array[i].addActionListener(this);
            clinic_select.add(clinic_array[i]);
        }
        return clinic_array;
    }
    public JMenuItem[] doctor_filling(String clinic_name){
        doctor_select.removeAll();
        List<Entities.Dentist> dentistList = dentistFunctions.filter_by_clinic(clinic_name);
        JMenuItem[] doc_array = new JMenuItem[dentistList.size()];
        for (int i = 0; i < doc_array.length; i++){
            doc_array[i] = new JMenuItem(dentistList.get(i).getDentist_name());
            doc_array[i].setActionCommand(dentistList.get(i).getDentist_name());
            doc_array[i].addActionListener(this);
            doctor_select.add(doc_array[i]);
        }
        return doc_array;
    }
}
