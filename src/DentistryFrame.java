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
    private static final String TIMETABLE = "TIMETABLE";
    private static final String DENTISTRY = "DENTISTRY";
    private static final String DENTIST = "DENTIST";
    private final JTable dTable = new JTable();
    private int category = 1;
    private final DentistFunctions dentistFunctions = new DentistFunctions();
    private final DentistryFunctions dentistryFunctions = new DentistryFunctions();
    private final TimetableFunctions timetableFunctions = new TimetableFunctions();
    private final AppointmentFunctions appointmentFunctions = new AppointmentFunctions();
    private final JMenu clinic_select;

    public DentistryFrame(){
        dTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        // Используем layout GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        // Каждый элемент является последним в строке
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        // Элемент раздвигается на весь размер ячейки
        gbc.fill = GridBagConstraints.BOTH;
        // Но имеет границы - слева, сверху и справа по 5. Снизу - 0
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
        JMenuItem pa = new JMenuItem("Приёмы");
        pa.setActionCommand(APPOINTMENT);
        pa.addActionListener(this);
        menu.add(pa);
        menuBar.add(menu);
        clinic_select = new JMenu("Выбор клиники");
        menuBar.add(clinic_select);
        clinic_select.setVisible(false);
        setJMenuBar(menuBar);
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(gridbag);
        btnPanel.add(createButton(gridbag, gbc, "Обновить", LOAD));
        btnPanel.add(createButton(gridbag, gbc, "Добавить", ADD));
        btnPanel.add(createButton(gridbag, gbc, "Исправить", EDIT));
        btnPanel.add(createButton(gridbag, gbc, "Удалить", DELETE));
        btnPanel.add(createButton(gridbag, gbc, "Сортировать", SORT));
        btnPanel.add(createButton(gridbag, gbc, "Поиск", SEARCH));
        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());
        left.add(btnPanel, BorderLayout.NORTH);
        add(left, BorderLayout.WEST);
        add(new JScrollPane(dTable), BorderLayout.CENTER);
        setBounds(100, 200, 1250, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    private JButton createButton(GridBagLayout gridbag, GridBagConstraints gbc, String title, String action) {
        JButton button = new JButton(title);
        button.setActionCommand(action);
        button.addActionListener(this);
        gridbag.setConstraints(button, gbc);
        return button;
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
            case LOAD -> loadData(category);
            case ADD -> addData(category);
            case EDIT -> editData(category);
            case DELETE -> deleteData(category);
            case SORT -> sortData(category);
            case SEARCH -> searchData(category);

        }
        JMenuItem[] items = clinic_filling();
        List<Entities.Dentistry> dentistries = dentistryFunctions.get_dentistry();
        for (int i = 0; i < items.length; i++) {
            //System.out.println(items[i].getActionCommand());
            if (action.equals(items[i].getActionCommand())) {
                getFilteredData(category, dentistries.get(i).getName());
            }
        }
    }
    private void loadData(int number){
        // 1 - врачи, 2 - клиники, 3 - приёмы, 4  - расписание
        switch (number) {
            case 1 -> {
                clinic_select.setVisible(true);
                clinic_filling();
                List<Entities.Dentist> dentistList = dentistFunctions.get_dentists();
                DentistTable dt = new DentistTable(dentistList);
                dTable.setModel(dt);
            }
            case 2 -> {
                clinic_select.setVisible(false);
                List<Entities.Dentistry> dentistryList = dentistryFunctions.get_dentistry();
                DentistryTable dryt = new DentistryTable(dentistryList);
                dTable.setModel(dryt);
            }
            case 3 -> {
                clinic_select.setVisible(true);
                clinic_filling();
                List<Entities.Appointments> appointmentsList = appointmentFunctions.get_appointments();
                AppointmentTable at = new AppointmentTable(appointmentsList);
                dTable.setModel(at);
            }
            case 4 -> {
                clinic_select.setVisible(true);
                clinic_filling();
                List<Entities.TimeTable> timeTableList = timetableFunctions.get_timetable();
                TimeTableTable ttt = new TimeTableTable(timeTableList);
                dTable.setModel(ttt);
             }
        }
    }
    private void getFilteredData(int category, String param){
        // 1 - врачи, 2 - клиники, 3 - приёмы, 4  - расписание
        switch (category){
            case 1 -> {
                List<Entities.Dentist> dentistList = dentistFunctions.filter_by_clinic(param);
                DentistTable dt = new DentistTable(dentistList);
                dTable.setModel(dt);
            }
            case 4 -> {
                List<Entities.TimeTable> timeTableList = timetableFunctions.filter_by_clinic(param);
                TimeTableTable ttt = new TimeTableTable(timeTableList);
                dTable.setModel(ttt);
            }


        }

    }
    private void addData(int category){
        // 1 - врачи, 2 - клиники, 3 - приёмы, 4  - расписание
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
            }
        }
    }
    public void editData(int category){
        switch (category){
            //case 1 -> editDentist();
            case 2 -> editDentistry();
            //case 3 -> editAppointment();
        }
    }
    public void deleteData(int category){
        switch (category){
            //case 1 -> editDentist();
            case 2 -> deleteDentistry();
            //case 3 -> editAppointment();
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
    private void saveDentistry(DentistryAddUpdDialog d){
        if (d.isSave()){
            Entities.Dentistry dentistry = d.getDentistry();
            if (dentistry.getDentistry_id() != 0){
                dentistryFunctions.updateDentistry(dentistry);
            }
            else{
                dentistryFunctions.addDentistry(dentistry);
            }
            loadData(2);
        }
    }
    private void saveDentist(DentistAddUpdDialog d){
        if (d.isSave()){
            Entities.Dentist dentist= d.getDentistry();
            if (dentist.getDentist_id() != 0){
                //dentistFunctions.updateDentist(dentist);
            }
            else{
                dentistFunctions.addDentist(dentist);
            }
            loadData(1);
        }
    }
    private void editDentistry() {
        int sr = dTable.getSelectedRow();
        if (sr != -1) {
            int id = Integer.parseInt(dTable.getModel().getValueAt(sr, 0).toString());
            DentistryAddUpdDialog dentistryAddUpdDialog = new
                    DentistryAddUpdDialog(dentistryFunctions.getDentistry(id));
            saveDentistry(dentistryAddUpdDialog);
        } else {
            JOptionPane.showMessageDialog(this, "Вы должны выделить строку для редактирования");
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
        List<Entities.Dentistry> dentistryList = dentistryFunctions.get_dentistry();
        JMenuItem[] clinic_array = new JMenuItem[dentistryList.size()];
        List<JMenuItem> clinic_list= new ArrayList<>();
        for (int i = 0; i < dentistryList.size(); i++){
            clinic_array[i] = new JMenuItem(dentistryList.get(i).getName());
            clinic_array[i].setActionCommand(dentistryList.get(i).getName());
            clinic_array[i].addActionListener(this);
            clinic_select.add(clinic_array[i]);
        }
        return clinic_array;
    }
}
