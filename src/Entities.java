public class Entities {
    private static final String[] week = new String[]{"Понедельник", "Вторник", "Среда", "Четверг",
            "Пятница", "Суббота", "Воскресенье"};
    private static final String[] dentist_type = new String[]{"стоматолог-хирург", "стоматолог-терапевт", "ортопед",
            "ортодонт", "стоматолог общей практики", "гигиенист", "детский стоматолог"};
    private static final String[] time_of_the_appointment = new String[]{"9:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00"};

    public static class Dentistry {
        private int dentistry_id;
        private String name;
        private String phone;
        private String head_of_clinic;
        private String address;
        private int foundation_year;
        private int customer_count;

        public int getDentistry_id() {
            return dentistry_id;
        }

        public void setDentistry_id(int dentistry_id) {
            this.dentistry_id = dentistry_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHead_of_clinic() {
            return head_of_clinic;
        }

        public void setHead_of_clinic(String head_of_clinic) {
            this.head_of_clinic = head_of_clinic;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getFoundation_year() {
            return foundation_year;
        }

        public void setFoundation_year(int foundation_year) {
            this.foundation_year = foundation_year;
        }

        public int getCustomer_count() {
            return customer_count;
        }

        public void setCustomer_count(int customer_count) {
            this.customer_count = customer_count;
        }


        public Dentistry(int id, String name, String phone, String head_of_clinic, String address,
                         int foundation_year, int customer_count) {
            this.dentistry_id = id;
            this.name = name;
            this.phone = phone;
            this.head_of_clinic = head_of_clinic;
            this.address = address;
            this.foundation_year = foundation_year;
            this.customer_count = customer_count;
        }
    }

    public static class Dentist {
        private int dentist_id;
        private String dentist_name;
        private String dentistry;
        private int experience;
        private String dentist_type;

        public int getDentist_id() {
            return dentist_id;
        }

        public void setDentist_id(int dentist_id) {
            this.dentist_id = dentist_id;
        }

        public String getDentist_name() {
            return dentist_name;
        }

        public void setDentist_name(String dentist_name) {
            this.dentist_name = dentist_name;
        }

        public String getDentistry() {
            return dentistry;
        }

        public void setDentistry(String dentistry) {
            this.dentistry = dentistry;
        }


        public int getExperience() {
            return experience;
        }

        public void setExperience(int experience) {
            this.experience = experience;
        }

        public String getDentist_type() {
            return dentist_type;
        }

        public void setDentist_type(String dentist_type) {
            this.dentist_type = dentist_type;
        }

        public Dentist(int id, String dentist_name, String dentistry, int experience,
                       String dentist_type) {
            this.dentist_id = id;
            this.dentist_name = dentist_name;
            this.dentistry = dentistry;
            this.experience = experience;
            this.dentist_type = dentist_type;
        }
    }

    public static class ForthcomingAppointment {
        private int appointment_id;
        private String dentistry;
        private String dentist;
        private String appointment_day;
        private String appointment_time;
        private String patient;

        public int getId() {
            return appointment_id;
        }

        public void setId(int id) {
            this.appointment_id = id;
        }

        public String getDentistry() {
            return dentistry;
        }

        public void setDentistry(String dentistry) {
            this.dentistry = dentistry;
        }

        public String getDentist() {
            return dentist;
        }

        public void setDentist(String dentist) {
            this.dentist = dentist;
        }

        public String getAppointment_day() {
            return appointment_day;
        }

        public void setAppointment_day(String appointment_day) {
            this.appointment_day = appointment_day;
        }

        public String getAppointment_time() {
            return appointment_time;
        }

        public void setAppointment_time(String appointment_time) {
            this.appointment_time = appointment_time;
        }

        public String getPatient() {return patient;}

        public void setPatient(String patient) {this.patient = patient;}

        public ForthcomingAppointment(int id, String dentistry, String dentist, String appointment_day,
                                      String appointment_time, String patient) {
            this.appointment_id = id;
            this.dentistry = dentistry;
            this.dentist = dentist;
            this.appointment_day = appointment_day;
            this.appointment_time = appointment_time;
            this.patient = patient;
        }
    }
    public static class PreviousAppointment{
        private int previous_appointment_id;
        private String dentistry;
        private String dentist;
        private String appointment_day;
        private String appointment_time;
        private String patient;
        private String diagnosis;
        private int admission_price;

        public int getPrevious_appointment_id() {return previous_appointment_id;}

        public void setPrevious_appointment_id(int previous_appointment_id) {this.previous_appointment_id = previous_appointment_id;}

        public String getDentistry() {return dentistry;}

        public void setDentistry(String dentistry) {this.dentistry = dentistry;}

        public String getDentist() {return dentist;}

        public void setDentist(String dentist) {this.dentist = dentist;}

        public String getAppointment_day() {return appointment_day;}

        public void setAppointment_day(String appointment_day) {this.appointment_day = appointment_day;}

        public String getAppointment_time() {return appointment_time;}

        public void setAppointment_time(String appointment_time) {this.appointment_time = appointment_time;}

        public String getPatient() {return patient;}

        public void setPatient(String patient) {this.patient = patient;}

        public String getDiagnosis() {return diagnosis;}

        public void setDiagnosis(String diagnosis) {this.diagnosis = diagnosis;}

        public int getAdmission_price() {return admission_price;}

        public void setAdmission_price(int admission_price) {this.admission_price = admission_price;}

        public PreviousAppointment(int previous_appointment_id, String dentistry, String dentist, String appointment_day,
                                   String appointment_time, String patient, String diagnosis, int admission_price) {
            this.previous_appointment_id = previous_appointment_id;
            this.dentistry = dentistry;
            this.dentist = dentist;
            this.appointment_day = appointment_day;
            this.appointment_time = appointment_time;
            this.patient = patient;
            this.diagnosis = diagnosis;
            this.admission_price = admission_price;
        }
    }

    public static class TimeTable {
        private int tt_id;
        private String dentist;
        private String dentistry;
        private String day;
        private String admission_time;

        public int getTt_id() {
            return tt_id;
        }

        public void setTt_id(int tt_id) {
            this.tt_id = tt_id;
        }

        public String getDentist() {
            return dentist;
        }

        public void setDentist(String dentist) {
            this.dentist = dentist;
        }

        public String getDentistry() {
            return dentistry;
        }

        public void setDentistry(String dentistry) {
            this.dentistry = dentistry;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getAdmission_time() {return admission_time;}

        public void setAdmission_time(String admission_time) {this.admission_time = admission_time;}


        public TimeTable(int tt_id, String dentist, String dentistry, String day, String admission_time) {
            this.tt_id = tt_id;
            this.dentist = dentist;
            this.dentistry = dentistry;
            this.day = day;
            this.admission_time = admission_time;
        }


    }
}
