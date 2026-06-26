package com.lehuuquynhnhi.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

//just sample...
public class DataWarehouse {
    public static ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        Category c1 = new Category("C1", "Rau xanh - Củ quả");
        Category c2 = new Category("C2", "Dầu ăn thực vật");
        Category c3 = new Category("C3", "Nước xốt");
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        return categories;
    }

    public static ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        //get catogeries
        ArrayList<Category> categories = getCategories();
        Product p1 = new Product("p1", "WINECO cà chua đỏ 500g", 10000, 10, 0, 0, categories.get(0).getCategoryId());
        Product p2 = new Product("p2", "Rau muống xanh chuẩn chuẩn Vietgap", 35, 30, 0, 0, categories.get(0).getCategoryId());
        Product p3 = new Product("p3", "Hành tây 350g", 10000, 15, 0, 0, categories.get(0).getCategoryId());
        Product p4 = new Product("p4", "Dầu oliu", 13500, 14, 0, 0, categories.get(0).getCategoryId());
        Product p5 = new Product("p5", "Nam Ngư", 17500, 22, 0, 0, categories.get(0).getCategoryId());
        Product p6 = new Product("p6", "Cholimex Food", 8500, 19, 0, 0, categories.get(0).getCategoryId());
        Product p7 = new Product("p7", "Súp lơ", 6500, 30, 0, 0, categories.get(0).getCategoryId());
        Product p8 = new Product("p8", "Bơ thực vật", 2000, 12, 0, 0, categories.get(0).getCategoryId());
        Product p9 = new Product("p9", "Chinsu", 15000, 4, 0, 0, categories.get(0).getCategoryId());

        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);
        products.add(p6);
        products.add(p7);
        products.add(p8);
        products.add(p9);

        return products;
    }

    public static ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("CUS1", "Nguyễn Văn A", "0901234567", "ana@example.com", "Hà Nội"));
        customers.add(new Customer("CUS2", "Trần Thị B", "0907654321", "bt@example.com", "TP. HCM"));
        customers.add(new Customer("CUS3", "Lê Văn C", "0912345678", "cle@example.com", "Đà Nẵng"));
        customers.add(new Customer("CUS4", "Phạm Thị D", "0918765432", "dpham@example.com", "Cần Thơ"));
        customers.add(new Customer("CUS5", "Hoàng Văn E", "0923456789", "ehoang@example.com", "Hải Phòng"));
        customers.add(new Customer("CUS6", "Vũ Thị F", "0929876543", "fvu@example.com", "Huế"));
        customers.add(new Customer("CUS7", "Đặng Văn G", "0934567890", "gdang@example.com", "Nha Trang"));
        customers.add(new Customer("CUS8", "Bùi Thị H", "0932109876", "hbui@example.com", "Vũng Tàu"));
        customers.add(new Customer("CUS9", "Lý Văn I", "0945678901", "ily@example.com", "Đà Lạt"));
        customers.add(new Customer("CUS10", "Ngô Thị J", "0943210987", "jngo@example.com", "Quảng Ninh"));
        return customers;
    }

    public static ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        employees.add(new Employee("E1", "Nguyễn Văn Nhân", "0900000001", "Hà Nội"));
        employees.add(new Employee("E2", "Trần Thị Sự", "0900000002", "TP. HCM"));
        employees.add(new Employee("E3", "Lê Văn Viên", "0900000003", "Đà Nẵng"));
        employees.add(new Employee("E4", "Phạm Thị Chức", "0900000004", "Cần Thơ"));
        employees.add(new Employee("E5", "Hoàng Văn Vụ", "0900000005", "Hải Phòng"));
        return employees;
    }

    public static ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        try {
            orders.add(new Order("O01", "CUS1", "E1", sdf.parse("20/05/2026 08:30"), OrderStatus.COMPLETED));
            orders.add(new Order("O02", "CUS2", "E2", sdf.parse("20/05/2026 09:15"), OrderStatus.NOT_YET_PAYMENT));
            orders.add(new Order("O03", "CUS3", "E3", sdf.parse("20/05/2026 10:05"), OrderStatus.GOING_LOGISTIC));
            orders.add(new Order("O04", "CUS4", "E4", sdf.parse("20/05/2026 11:20"), OrderStatus.CUSTOMER_COMPLAIN));
            orders.add(new Order("O05", "CUS5", "E5", sdf.parse("20/05/2026 13:10"), OrderStatus.ALL));
            orders.add(new Order("O06", "CUS6", "E1", sdf.parse("21/05/2026 14:25"), OrderStatus.ALL));
            orders.add(new Order("O07", "CUS7", "E2", sdf.parse("21/05/2026 15:40"), OrderStatus.ALL));
            orders.add(new Order("O08", "CUS8", "E3", sdf.parse("21/05/2026 16:30"), OrderStatus.ALL));
            orders.add(new Order("O09", "CUS9", "E4", sdf.parse("21/05/2026 18:00"), OrderStatus.ALL));
            orders.add(new Order("O10", "CUS10", "E5", sdf.parse("21/05/2026 19:15"), OrderStatus.ALL));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public static ArrayList<OrderDetail> getOrderDetails(ArrayList<Order> orders, ArrayList<Product> products)
    {
        ArrayList<OrderDetail> orderDetails=new ArrayList<>();

        int detailIndex = 1;
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            int detailCount = (i % 10) + 1;

            for (int j = 0; j < detailCount; j++) {
                Product product = products.get((i + j) % products.size());
                int maxQuantity = Math.max(1, product.getQuantity());
                int quantity = ((i + j) % maxQuantity) + 1;

                orderDetails.add(new OrderDetail(
                        "od" + detailIndex,
                        order.getOrderId(),
                        product.getProductId(),
                        quantity,
                        product.getPrice(),
                        product.getCoupon(),
                        product.getVAT()
                ));
                detailIndex++;
            }
        }
        return orderDetails;

    }

    public static ArrayList<OrderDetail> getOrderDetails() {
        ArrayList<OrderDetail> details = new ArrayList<>();
        SimpleDateFormat sdf =
                new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        details.add(new OrderDetail("OD001", "O01", "P1", 1, 25900, 5180, 0));
        details.add(new OrderDetail("OD002", "O01", "P2", 2, 13500, 0, 0));
        details.add(new OrderDetail("OD003", "O01", "P3", 1, 12500, 0, 0));
        details.add(new OrderDetail("OD004", "O01", "P4", 1, 12000, 0, 0));
        details.add(new OrderDetail("OD005", "O01", "P5", 1, 18000, 0, 0));

        details.add(new OrderDetail("OD006", "O02", "P1", 1, 25900, 5180, 0));
        details.add(new OrderDetail("OD007", "O02", "P3", 2, 12500, 0, 0));
        details.add(new OrderDetail("OD008", "O02", "P5", 1, 18000, 0, 0));
        details.add(new OrderDetail("OD009", "O02", "P6", 1, 22000, 0, 0));
        details.add(new OrderDetail("OD010", "O02", "P9", 1, 89000, 5000, 0));

        details.add(new OrderDetail("OD011", "O03", "P2", 1, 13500, 0, 0));
        details.add(new OrderDetail("OD012", "O03", "P4", 2, 12000, 0, 0));
        details.add(new OrderDetail("OD013", "O03", "P6", 1, 22000, 0, 0));
        details.add(new OrderDetail("OD014", "O03", "P7", 1, 35000, 0, 0));
        details.add(new OrderDetail("OD015", "O03", "P8", 2, 18000, 0, 0));
        details.add(new OrderDetail("OD016", "O03", "P9", 1, 89000, 5000, 0));

        details.add(new OrderDetail("OD017", "O04", "P1", 2, 25900, 5180, 0));
        details.add(new OrderDetail("OD018", "O04", "P2", 1, 13500, 0, 0));
        details.add(new OrderDetail("OD019", "O04", "P3", 1, 12500, 0, 0));
        details.add(new OrderDetail("OD020", "O04", "P6", 2, 22000, 0, 0));
        details.add(new OrderDetail("OD021", "O04", "P8", 1, 18000, 0, 0));

        details.add(new OrderDetail("OD022", "O05", "P4", 1, 12000, 0, 0));
        details.add(new OrderDetail("OD023", "O05", "P5", 2, 18000, 0, 0));
        details.add(new OrderDetail("OD024", "O05", "P6", 1, 22000, 0, 0));
        details.add(new OrderDetail("OD025", "O05", "P7", 1, 35000, 0, 0));
        details.add(new OrderDetail("OD026", "O05", "P9", 1, 89000, 5000, 0));

        details.add(new OrderDetail("OD027", "O06", "P1", 1, 25900, 5180, 0));
        details.add(new OrderDetail("OD028", "O06", "P2", 1, 13500, 0, 0));
        details.add(new OrderDetail("OD029", "O06", "P3", 2, 12500, 0, 0));
        details.add(new OrderDetail("OD030", "O06", "P4", 1, 12000, 0, 0));
        details.add(new OrderDetail("OD031", "O06", "P5", 1, 18000, 0, 0));
        details.add(new OrderDetail("OD032", "O06", "P8", 2, 18000, 0, 0));

        details.add(new OrderDetail("OD033", "O07", "P2", 2, 13500, 0, 0));
        details.add(new OrderDetail("OD034", "O07", "P4", 1, 12000, 0, 0));
        details.add(new OrderDetail("OD035", "O07", "P5", 1, 18000, 0, 0));
        details.add(new OrderDetail("OD036", "O07", "P6", 1, 22000, 0, 0));
        details.add(new OrderDetail("OD037", "O07", "P7", 1, 35000, 0, 0));

        details.add(new OrderDetail("OD038", "O08", "P1", 1, 25900, 5180, 0));
        details.add(new OrderDetail("OD039", "O08", "P3", 1, 12500, 0, 0));
        details.add(new OrderDetail("OD040", "O08", "P5", 2, 18000, 0, 0));
        details.add(new OrderDetail("OD041", "O08", "P8", 1, 18000, 0, 0));
        details.add(new OrderDetail("OD042", "O08", "P9", 1, 89000, 5000, 0));

        details.add(new OrderDetail("OD043", "O09", "P1", 2, 25900, 5180, 0));
        details.add(new OrderDetail("OD044", "O09", "P2", 2, 13500, 0, 0));
        details.add(new OrderDetail("OD045", "O09", "P4", 1, 12000, 0, 0));
        details.add(new OrderDetail("OD046", "O09", "P6", 1, 22000, 0, 0));
        details.add(new OrderDetail("OD047", "O09", "P7", 1, 35000, 0, 0));
        details.add(new OrderDetail("OD048", "O09", "P8", 1, 18000, 0, 0));

        details.add(new OrderDetail("OD049", "O10", "P3", 2, 12500, 0, 0));
        details.add(new OrderDetail("OD050", "O10", "P4", 1, 12000, 0, 0));
        details.add(new OrderDetail("OD051", "O10", "P5", 1, 18000, 0, 0));
        details.add(new OrderDetail("OD052", "O10", "P6", 2, 22000, 0, 0));
        details.add(new OrderDetail("OD053", "O10", "P9", 1, 89000, 5000, 0));
        return details;
    }
    public static double sumOfMoney(Order od) {
        double sum = 0;
        ArrayList<OrderDetail> details = getOrderDetails();
        for (OrderDetail detail : details) {
            if (detail.getOrderId().equals(od.getOrderId())) {
                // Công thức: (Đơn giá * Số lượng) * (1 - %Giảm giá) * (1 + %VAT)
                double lineTotal = detail.getPrice() * detail.getQuantity();
                double afterCoupon = lineTotal * (1 - detail.getCoupon());
                double afterVAT = afterCoupon * (1 + detail.getVAT());
                sum += afterVAT;
            }
        }
        return sum;
    }

    public static ArrayList<Order> filterOrdersByDate(Date fromDate, Date toDate) {
        ArrayList<Order> orders = getOrders();
        ArrayList<Order> result_filter = new ArrayList<>();

        // Chuẩn hóa fromDate về đầu ngày (00:00:00.000)
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(fromDate);
        calFrom.set(Calendar.HOUR_OF_DAY, 0);
        calFrom.set(Calendar.MINUTE, 0);
        calFrom.set(Calendar.SECOND, 0);
        calFrom.set(Calendar.MILLISECOND, 0);
        Date start = calFrom.getTime();

        // Chuẩn hóa toDate về cuối ngày (23:59:59.999)
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(toDate);
        calTo.set(Calendar.HOUR_OF_DAY, 23);
        calTo.set(Calendar.MINUTE, 59);
        calTo.set(Calendar.SECOND, 59);
        calTo.set(Calendar.MILLISECOND, 999);
        Date end = calTo.getTime();

        for (Order order : orders) {
            Date orderDate = order.getOrderDate();
            // Kiểm tra: start <= orderDate <= end
            if (orderDate != null && !orderDate.before(start) && !orderDate.after(end)) {
                result_filter.add(order);
            }
        }

        return result_filter;
    }
    public static ArrayList<Order> filterOrdersByStatus(OrderStatus status) {
        ArrayList<Order> orders = getOrders();
        if (status == OrderStatus.ALL)
            return orders;
        ArrayList<Order> result_filter = new ArrayList<>();
        for (Order order : orders) {
            if (order.getOrderStatus() == status) {
                result_filter.add(order);
            }
        }
        return result_filter;
        }
    }