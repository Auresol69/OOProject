public class vd {

    public static void main(String[] args) {
        RoomManager rmng = new RoomManager();
        CustomerManager cmng = new CustomerManager();
        ServiceManager svmng = new ServiceManager();

        BookingManager bmng = new BookingManager(rmng, cmng, svmng);
        for (Booking b : bmng.getBookings()){
        }

        rmng.history();
        Booking b = new Booking();     
        b.set_calendar_and_service(rmng,svmng);   
        bmng.add_booking(b);
        System.out.println("consad s");
        


        rmng.history();
       
        
        
    }
}