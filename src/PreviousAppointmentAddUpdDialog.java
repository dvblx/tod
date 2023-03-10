import org.postgresql.jdbc.PgArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreviousAppointmentAddUpdDialog extends JDialog implements ActionListener {
    private static final String SAVE = "Сохранить";
    private static final String CANCEL = "Закрыть";
    private static final int PAD = 10;
    private static final int W_L = 100;
    private static final int W_T = 300;
    private static final int W_B = 120;
    private static final int H_B = 25;
    private JTextPane diagnosis_input = new JTextPane();
    private JTextPane price_input = new JTextPane();
    private JButton btnSave = new JButton("Сохранить");
    private JButton btnCancel = new JButton("Закрыть");
    private boolean save = false;
    private int appoontmentId = 0;

    public PreviousAppointmentAddUpdDialog(Entities.PreviousAppointment appointment){
        setLayout(null);
        buildFields();
        buildButtons();
        setModal(true);
        setResizable(false);
        setBounds(300, 300, 450, 200);
        setVisible(true);
    }
    public void buildButtons() {
        btnSave.setActionCommand(SAVE);
        btnSave.addActionListener(this);
        btnSave.setBounds(new Rectangle(PAD, 4 * H_B + PAD, W_B, H_B));
        btnSave.setVisible(true);
        add(btnSave);
        btnCancel.setActionCommand(CANCEL);
        btnCancel.addActionListener(this);
        btnCancel.setBounds(new Rectangle(W_B + 2 * PAD, 4 * H_B + PAD, W_B,
                H_B));
        btnCancel.setVisible(true);
        add(btnCancel);
    }

    public void buildFields() {
        JLabel dgns = new JLabel("Диагноз: ");
        dgns.setHorizontalAlignment(SwingConstants.LEFT);
        dgns.setBounds(new Rectangle(PAD, 0 * H_B + PAD, W_L, H_B));
        add(dgns);
        diagnosis_input.setBounds(new Rectangle(W_L + 2 * PAD, 0 * H_B + PAD,
                W_T, H_B));
        diagnosis_input.setBorder(BorderFactory.createEtchedBorder());
        add(diagnosis_input);
        JLabel prc = new JLabel("Стоимость: ");
        prc.setHorizontalAlignment(SwingConstants.LEFT);
        prc.setBounds(new Rectangle(PAD, 2 * H_B + PAD, W_L, H_B));
        add(prc);
        price_input.setBounds(new Rectangle(W_L + 2 * PAD, 2 * H_B + PAD,
                W_T, H_B));
        price_input.setBorder(BorderFactory.createEtchedBorder());
        add(price_input);

    }

    public Entities.PreviousAppointment getAppointment(Entities.PreviousAppointment pa){
        return new Entities.PreviousAppointment(pa.getPrevious_appointment_id(), pa.getDentistry(), pa.getDentist(),
                pa.getAppointment_day(), pa.getAppointment_time(), pa.getPatient(), diagnosis_input.getText(),
                Integer.parseInt(price_input.getText()));
    }

    public boolean isSave() {
        return save;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        save = SAVE.equals(action);
        setVisible(false);
    }

}
