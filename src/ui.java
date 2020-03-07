import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class ui{
    private static JFrame frame = new JFrame("YELP");
    private static JPanel mainPanel = new JPanel();
    private static JPanel topPanel = new JPanel();
    private static JPanel downPanel = new JPanel();

    private static JPanel mainCatgPanel = new JPanel();
    private static JPanel subCatgPanel = new JPanel();
    private static JPanel attributePanel = new JPanel();
    private static JPanel resultPanel = new JPanel();
    private static JPanel dayPanel = new JPanel();
    private static JPanel timeStartPanel = new JPanel();
    private static JPanel timeEndPanel = new JPanel();
    private static JPanel relationPanel = new JPanel();
    private static JPanel localtionPanel = new JPanel();
    private static JPanel rightDownPanel = new JPanel();
    private static JPanel countSubPanel = new JPanel();
    private static JPanel countAttriPanel = new JPanel();
    private static JPanel countResultPanel = new JPanel();
    private static JPanel countReviewsPanel = new JPanel();


    private static JLabel mainCatg = new JLabel("mainCatg", JLabel.CENTER);
    private static JLabel subCatg = new JLabel("subCatg", JLabel.CENTER);
    private static JLabel attribute = new JLabel("Attribute", JLabel.CENTER);
    private static JLabel day = new JLabel("Day", JLabel.CENTER);
    private static JLabel from = new JLabel("From");
    private static JLabel to = new JLabel("To ");
    private static JLabel searchFor = new JLabel("Search for", JLabel.CENTER);
    private static JLabel Result = new JLabel("Business Result", JLabel.CENTER);
    private static JLabel location = new JLabel("City, State", JLabel.CENTER);
    private static JLabel countSubCatg = new JLabel("SubCatg #", JLabel.CENTER);
    private static JLabel countAttri = new JLabel("Attri #", JLabel.CENTER);
    private static JLabel countResult = new JLabel("Result #", JLabel.CENTER);
    private static JLabel countReviews = new JLabel("Review #", JLabel.CENTER);

    private static JScrollPane mainCatgScroll = new JScrollPane();
    private static JScrollPane subCatgScroll = new JScrollPane();
    private static JScrollPane attributeScroll = new JScrollPane();
    private static JScrollPane resultScroll = new JScrollPane();

    private static JList<Object> mainCatgList = new JList<>();
    private static JList<Object> subCatgList = new JList<>();
    private static JList<Object> attributeCatgList = new JList<>();

    private static JComboBox<String> openDay = new JComboBox<>();
    private static JComboBox<String> startTime = new JComboBox<>();
    private static JComboBox<String> endTime = new JComboBox<>();
    private static JComboBox<String> andOR = new JComboBox<>();
    private static JComboBox<String> stateCity = new JComboBox<>();
    private static DefaultListModel<Object> mainCatgListModel = new DefaultListModel<>();
    private static DefaultListModel<Object> subCatgListModel = new DefaultListModel<>();
    private static DefaultListModel<Object> attributeCatgListModel = new DefaultListModel<>();
    private static DefaultTableModel tableModel = new DefaultTableModel();

    private static JButton search = new JButton("Search");
    private static JButton close = new JButton("Close");
    private static JButton update = new JButton("show SubDayLoca");
    private static JButton showAttribute = new JButton("Show Attri");

    private static JTextField numSubCatg = new JTextField();
    private static JTextField numAttri = new JTextField();
    private static JTextField numResult = new JTextField();
    private static JTextField numReviews = new JTextField();

    private static JTable resultTable = new JTable();

    private static Connection connection;
    private static String[] selectedMainCatg;
    private static String[] selectedSubCatg;
    private static String[] selectedAttributes;
    private static String selectedRelation;
    private static String selectedDay;
    private static String selectedStartTime;
    private static String selectedEndTime;
    private static String selectedLocation;
    private static String chooseCity;
    private static String chooseState;


    private ui(){
        //main layout
        mainPanel.setLayout(null);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(downPanel, BorderLayout.SOUTH);

        //top layout
        topPanel.setLayout(null);
        topPanel.setBounds(0, 10, 1360, 460);
        topPanel.add(mainCatgPanel);
        mainCatgPanel.setBounds(10, 10, 200, 460);
        topPanel.add(subCatgPanel);
        subCatgPanel.setBounds(215, 10, 200, 460);
        topPanel.add(attributePanel);
        attributePanel.setBounds(420, 10, 265, 460);
        topPanel.add(resultPanel);
        resultPanel.setBounds(690, 10, 535, 460);

        //button layout
        downPanel.setLayout(new GridLayout(1, 9));
        downPanel.setBounds(10, 480, 1210, 90);

        downPanel.add(dayPanel);
        dayPanel.setBounds(10, 480, 60, 70);
        downPanel.add(timeStartPanel);
        timeStartPanel.setBounds(70, 480, 60, 70);
        downPanel.add(timeEndPanel);
        timeEndPanel.setBounds(140, 480, 60, 70);
        downPanel.add(relationPanel);
        relationPanel.setBounds(210, 480, 60, 70);
        downPanel.add(localtionPanel);
        localtionPanel.setBounds(280, 480, 60, 70);
        downPanel.add(rightDownPanel);
        downPanel.add(countSubPanel);
        downPanel.add(countAttriPanel);
        downPanel.add(countResultPanel);
        //downPanel.add(countReviewsPanel);


        //downPane
        rightDownPanel.setLayout(null);
        rightDownPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        rightDownPanel.add(update);
        rightDownPanel.add(showAttribute);
        rightDownPanel.add(search);
        rightDownPanel.add(close);
        update.setBounds(6, 5, 110, 15);
        showAttribute.setBounds(6, 25, 110, 15);
        search.setBounds(6, 45, 110, 15);
        close.setBounds(6, 65, 110, 15);

        //main
        mainCatgPanel.setLayout(new BorderLayout());
        mainCatgPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        mainCatgPanel.add(mainCatg, BorderLayout.NORTH);
        mainCatgPanel.add(mainCatgScroll);

        mainCatg.setPreferredSize(new Dimension(20, 30));
        mainCatgScroll.setViewportView(mainCatgList);
        mainCatgList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        mainCatgList.setModel(mainCatgListModel);

        //submain
        subCatgPanel.setLayout(new BorderLayout());
        subCatgPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        subCatgPanel.add(subCatg, BorderLayout.NORTH);
        subCatgPanel.add(subCatgScroll);

        subCatg.setPreferredSize(new Dimension(20, 30));
        subCatgScroll.setViewportView(subCatgList);
        subCatgList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        subCatgList.setModel(subCatgListModel);

        //attribute
        attributePanel.setLayout(new BorderLayout());
        attributePanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        attributePanel.add(attribute, BorderLayout.NORTH);
        attributePanel.add(attributeScroll);

        attribute.setPreferredSize(new Dimension(20, 30));
        attributeScroll.setViewportView(attributeCatgList);
        attributeCatgList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        attributeCatgList.setModel(attributeCatgListModel);

        //result
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        resultPanel.add(Result, BorderLayout.NORTH);
        resultPanel.add(resultScroll);
        //resultTable.setBounds(710, 25, 400,400);

        Result.setPreferredSize(new Dimension(20, 30));
        resultScroll.setViewportView(resultTable);
        resultTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        resultTable.setModel(tableModel);
        tableModel.addColumn("bid");
        tableModel.addColumn("addr");
        tableModel.addColumn("city");
        tableModel.addColumn("state");
        tableModel.addColumn("stars");
        tableModel.addColumn("numReviews");
        tableModel.addColumn("checkin_number");

        //day
        dayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dayPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        dayPanel.add(day, BorderLayout.NORTH);
        day.setPreferredSize(new Dimension(30, 30));
        dayPanel.add(openDay);
        openDay.setPreferredSize(new Dimension(120, 20));

        //from
        timeStartPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        timeStartPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        timeStartPanel.add(from);
        from.setPreferredSize(new Dimension(30, 30));
        timeStartPanel.add(startTime);
        startTime.setPreferredSize(new Dimension(120, 20));

        //to
        timeEndPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        timeEndPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        timeEndPanel.add(to);
        to.setPreferredSize(new Dimension(30, 30));
        timeEndPanel.add(endTime);
        endTime.setPreferredSize(new Dimension(120, 20));

        //search for
        relationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        relationPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        relationPanel.add(searchFor);
        searchFor.setPreferredSize(new Dimension(75, 30));
        relationPanel.add(andOR);
        andOR.setPreferredSize(new Dimension(120, 20));
        andOR.setBackground(Color.WHITE);
        andOR.addItem("AND");
        andOR.addItem("OR");

        //location
        localtionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        localtionPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        localtionPanel.add(location);
        location.setPreferredSize(new Dimension(75, 30));
        localtionPanel.add(stateCity);
        stateCity.setPreferredSize(new Dimension(120, 20));

        //count
        countSubPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        countSubPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        countSubPanel.add(countSubCatg);
        countSubCatg.setPreferredSize(new Dimension(80, 30));
        countSubPanel.add(numSubCatg);
        numSubCatg.setPreferredSize(new Dimension(100, 20));

        countAttriPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        countAttriPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        countAttriPanel.add(countAttri);
        countAttri.setPreferredSize(new Dimension(80, 30));
        countAttriPanel.add(numAttri);
        numAttri.setPreferredSize(new Dimension(100, 20));

        countResultPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        countResultPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        countResultPanel.add(countResult);
        countResult.setPreferredSize(new Dimension(80, 30));
        countResultPanel.add(numResult);
        numResult.setPreferredSize(new Dimension(100, 20));

//        countReviewsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
//        countReviewsPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
//        countReviewsPanel.add(countReviews);
//        countReviews.setPreferredSize(new Dimension(80, 30));
//        countReviewsPanel.add(numReviews);
//        numReviews.setPreferredSize(new Dimension(100, 20));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setBounds(10,10,1250, 650);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static void dbConnection() {
        // Connect to the Database
        System.out.println("Connecting to Database ...");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error Loading Driver: " + e);
        }
        String hostName = "localhost";
        String dbName = "ss";
        int port = 1521;

        String oracleURL = "jdbc:oracle:thin:@" + hostName + ":" + port + ":" + dbName;
        String username = "scott";
        String password = "tiger";

        try {
            connection = DriverManager.getConnection(oracleURL, username, password);
            System.out.println("Database Connect Successful!");
        } catch (SQLException se) {
            System.out.println("Connection Error: " + se);
        }
    }

    private static void initialization() {
        try {
            Statement statement = connection.createStatement();
            String loadMainCatg = "SELECT mcname FROM mainCatg";
            ResultSet rs = statement.executeQuery(loadMainCatg);

            while (rs.next()) {
                mainCatgListModel.addElement(rs.getString(1));
                //System.out.print(rs.getString(1) + ",");
            }
            openDay.addItem("ALL");
            startTime.addItem("ALL");
            endTime.addItem("ALL");
            stateCity.addItem("ALL");

            openDay.setSelectedItem("ALL");
            startTime.setSelectedItem("ALL");
            endTime.setSelectedItem("ALL");
            stateCity.setSelectedItem("ALL");
            andOR.setSelectedItem("AND");

            selectedRelation = andOR.getSelectedItem().toString();
            selectedDay = openDay.getSelectedItem().toString();
            selectedStartTime = startTime.getSelectedItem().toString();
            selectedEndTime = endTime.getSelectedItem().toString();
            selectedLocation = stateCity.getSelectedItem().toString();
            chooseCity = "";
            chooseState = "";

        } catch (SQLException se) {
            System.out.println("Error Initialize: " + se);
        }
    }

    private static void mainCatgListener() {
        mainCatgList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    List<Object> values = mainCatgList.getSelectedValuesList();
                    selectedMainCatg = values.toArray(new String[values.size()]);
                    //System.out.println("Selected MainCatg: " + Arrays.toString(selectedMainCatg));
                }
            }
        });
    }

    private static void dayListener() {
        openDay.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedDay = openDay.getSelectedItem().toString();
                    System.out.println("Selected Start Day: " + selectedDay);
                }
            }
        });
    }

    private static void hourListener() {
        startTime.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedStartTime = startTime.getSelectedItem().toString();
                    //System.out.println("Selected Start Time: " + selectedStartTime);
                }
            }
        });

        endTime.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedEndTime = endTime.getSelectedItem().toString();
                    //System.out.println("Selected End Time: " + selectedEndTime);
                }
            }
        });
    }

    private static void relationListener() {
        andOR.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedRelation = andOR.getSelectedItem().toString();

                    //System.out.println("Selected Relation: " + selectedRelation);
                }
            }
        });
    }

    private static void locationListener() {
        stateCity.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedLocation = stateCity.getSelectedItem().toString();
                    //System.out.println("Selected Location: " + selectedLocation);
                }
            }
        });
    }

    private static void attributeListener() {
        attributeCatgList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    List<Object> values = attributeCatgList.getSelectedValuesList();
                    selectedAttributes = values.toArray(new String[values.size()]);
                    //System.out.println("Selected Attributes: " + Arrays.toString(selectedAttributes));
                }
            }
        });
    }

    private static void subCatgListener() {
        subCatgList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    List<Object> values = subCatgList.getSelectedValuesList();
                    selectedSubCatg = values.toArray(new String[values.size()]);
                    //System.out.println("Selected SubCatg: " + Arrays.toString(selectedSubCatg));
                }
            }
        });
    }

    private static void showSubDayLoca() {
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sbSubCatg = new StringBuilder();
                StringBuilder sbDay = new StringBuilder();
                StringBuilder sbLocation = new StringBuilder();
                for (int i = 0; i < selectedMainCatg.length; i++) {
                    sbSubCatg.append(String.format("(SELECT scname FROM subCatg WHERE mcname = '%s')", selectedMainCatg[i]));
                    sbDay.append(String.format("(SELECT DISTINCT day FROM BusinessHour bh, main_busi mb WHERE '%s' = mb.mcname AND mb.bid = bh.bid)", selectedMainCatg[i]));
                    sbLocation.append(String.format("(SELECT DISTINCT concat(concat(city, ','), state) FROM Business b, main_busi mb WHERE mb.mcname = '%s' AND mb.bid = b.bid)", selectedMainCatg[i]));
                    if (selectedRelation == "AND"){
                        sbSubCatg.append("INTERSECT");
                        sbDay.append("INTERSECT");
                        sbLocation.append("INTERSECT");
                    } else {//if (selectedRelation == "OR"){
                        sbSubCatg.append("UNION");
                        sbDay.append("UNION");
                        sbLocation.append("UNION");
                    }
                }
                sbSubCatg.setLength(sbSubCatg.length() - (selectedRelation == "AND" ? 9 : 5));
                sbDay.setLength(sbDay.length() - (selectedRelation == "AND" ? 9 : 5));
                sbLocation.setLength(sbLocation.length() - (selectedRelation == "AND" ? 9 : 5));

                // Reset UI
                subCatgListModel.removeAllElements();
                openDay.removeAllItems();
                stateCity.removeAllItems();
                startTime.removeAllItems();
                endTime.removeAllItems();
                openDay.addItem("ALL");
                startTime.addItem("ALL");
                endTime.addItem("ALL");
                stateCity.addItem("ALL");

                // Reset Data
                openDay.setSelectedItem("ALL");
                startTime.setSelectedItem("ALL");
                endTime.setSelectedItem("ALL");
                stateCity.setSelectedItem("ALL");
                selectedDay = openDay.getSelectedItem().toString();
                selectedStartTime = startTime.getSelectedItem().toString();
                selectedEndTime = endTime.getSelectedItem().toString();
                selectedLocation = stateCity.getSelectedItem().toString();


                try {
                    PreparedStatement statement = connection.prepareStatement(sbSubCatg.toString());
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        subCatgListModel.addElement(rs.getString(1));
                    }
                } catch (SQLException se) {
                    System.out.println("SQLException occurs: " + se);
                }

                try {
                    PreparedStatement statement1 = connection.prepareStatement(sbDay.toString());
                    ResultSet rs1 = statement1.executeQuery();
                    while (rs1.next()) {
                        openDay.addItem(rs1.getString(1));
                    }
                } catch (SQLException se) {
                    System.out.println("SQLException occurs: " + se);
                }

                try {
                    PreparedStatement statement2 = connection.prepareStatement(sbLocation.toString());
                    ResultSet rs2 = statement2.executeQuery();
                    while (rs2.next()) {
                        stateCity.addItem(rs2.getString(1));
                    }
                } catch (SQLException se) {
                    System.out.println("SQLException occurs: " + se);
                }

                numSubCatg.setText(String.valueOf(subCatgListModel.size()));
            }
        });
    }

    private static void showAttribute() {
        showAttribute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                StringBuilder sbDay = new StringBuilder();
                StringBuilder sbLocation = new StringBuilder();
                for (int j = 0; j <selectedMainCatg.length; j++) {
                    for (int i = 0; i < selectedSubCatg.length; i++) {
                        sb.append(String.format("(SELECT avalue FROM attr_catg WHERE mcname = '%s' AND scname = '%s')", selectedMainCatg[j], selectedSubCatg[i]));
                        sbDay.append(String.format("(SELECT DISTINCT day FROM BusinessHour bh, main_busi mb, sub_busi sb WHERE '%s' = mb.mcname AND sb.scname = '%s' AND mb.bid = bh.bid AND sb.bid = bh.bid)", selectedMainCatg[j], selectedSubCatg[i]));
                        sbLocation.append(String.format("(SELECT DISTINCT concat(concat(city, ','), state) FROM Business b, main_busi mb , sub_busi sb WHERE mb.mcname = '%s' AND sb.scname = '%s' AND mb.bid = b.bid AND sb.bid = b.bid)", selectedMainCatg[j], selectedSubCatg[i]));
                        if (selectedRelation == "AND"){
                            sb.append("INTERSECT");
                            sbDay.append("INTERSECT");
                            sbLocation.append("INTERSECT");
                        } else {//if (selectedRelation == "OR"){
                            sb.append("UNION");
                            sbDay.append("UNION");
                            sbLocation.append("UNION");
                        }
                    }
                }
                sb.setLength(sb.length() - (selectedRelation == "AND" ? 9 : 5));
                sbDay.setLength(sbDay.length() - (selectedRelation == "AND" ? 9 : 5));
                sbLocation.setLength(sbLocation.length() - (selectedRelation == "AND" ? 9 : 5));

                //System.out.println(sbDay.toString());
                // Reset UI
                attributeCatgListModel.removeAllElements();
                openDay.removeAllItems();
                stateCity.removeAllItems();
                startTime.removeAllItems();
                endTime.removeAllItems();
                openDay.addItem("ALL");
                startTime.addItem("ALL");
                endTime.addItem("ALL");
                stateCity.addItem("ALL");

                // Reset Data
                openDay.setSelectedItem("ALL");
                startTime.setSelectedItem("ALL");
                endTime.setSelectedItem("ALL");
                stateCity.setSelectedItem("ALL");
                selectedDay = openDay.getSelectedItem().toString();
                selectedStartTime = startTime.getSelectedItem().toString();
                selectedEndTime = endTime.getSelectedItem().toString();
                selectedLocation = stateCity.getSelectedItem().toString();


                try {
                    PreparedStatement statement = connection.prepareStatement(sb.toString());
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        attributeCatgListModel.addElement(rs.getString(1));
                    }
                } catch (SQLException se) {
                    System.out.println("SQLException occurs: " + se);
                }

                try {
                    PreparedStatement statement1 = connection.prepareStatement(sbDay.toString());
                    ResultSet rs1 = statement1.executeQuery();
                    while (rs1.next()) {
                        openDay.addItem(rs1.getString(1));
                    }
                } catch (SQLException se) {
                    System.out.println("SQLException occurs: " + se);
                }

                try {
                    PreparedStatement statement2 = connection.prepareStatement(sbLocation.toString());
                    ResultSet rs2 = statement2.executeQuery();
                    while (rs2.next()) {
                        stateCity.addItem(rs2.getString(1));
                    }
                } catch (SQLException se) {
                    System.out.println("SQLException occurs: " + se);
                }

                numAttri.setText(String.valueOf(attributeCatgListModel.size()));
            }
        });
    }

    private static void showHour(){
        openDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j <selectedMainCatg.length; j++) {
                    if (selectedSubCatg == null || selectedSubCatg.length == 0) {
                        if (selectedDay.equals("ALL")) {
                            sb.append(String.format("(SELECT DISTINCT open, close FROM BusinessHour bh, main_busi mb WHERE mb.mcname = '%s' AND mb.bid = bh.bid)", selectedMainCatg[j]));
                        } else {
                            sb.append(String.format("(SELECT DISTINCT open, close FROM BusinessHour bh, main_busi mb WHERE mb.mcname = '%s' AND mb.bid = bh.bid AND bh.day = '%s')", selectedMainCatg[j], selectedDay));
                        }
                        sb.append("UNION");
                    } else {
                        for (int i = 0; i < selectedSubCatg.length; i++) {
                            if (selectedDay.equals("ALL")) {
                                sb.append(String.format("(SELECT DISTINCT open, close FROM BusinessHour bh, main_busi mb, sub_busi sb WHERE mb.mcname = '%s' AND sb.scname = '%s' AND sb.bid = bh.bid AND mb.bid = bh.bid)", selectedMainCatg[j], selectedSubCatg[i]));
                            } else {
                                sb.append(String.format("(SELECT DISTINCT open, close FROM BusinessHour bh, main_busi mb, sub_busi sb  WHERE mb.mcname = '%s' AND sb.scname = '%s' AND sb.bid = bh.bid AND mb.bid = bh.bid AND bh.day = '%s')", selectedMainCatg[j], selectedSubCatg[i], selectedDay));
                            }
                            sb.append("UNION");
                        }
                    }
                }

                sb.setLength(sb.length() - 5);

                //System.out.println(sbOpen.toString());
                // Reset UI
                startTime.removeAllItems();
                endTime.removeAllItems();
                startTime.addItem("ALL");
                endTime.addItem("ALL");

                // Reset Data
                startTime.setSelectedItem("ALL");
                endTime.setSelectedItem("ALL");
                selectedStartTime = startTime.getSelectedItem().toString();
                selectedEndTime = endTime.getSelectedItem().toString();


                try {
                    PreparedStatement statement = connection.prepareStatement(sb.toString());
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        startTime.addItem(rs.getString(1));
                        endTime.addItem(rs.getString(2));
                    }
                } catch (SQLException se) {
                    System.out.println("SQLException occurs: " + se);
                }
            }
        });


    }

    private static void showResult() {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j <selectedMainCatg.length; j++) {
                    if (selectedSubCatg == null || selectedSubCatg.length == 0) {
                        sb.append(String.format("(SELECT Distinct b.bid, b.addr, b.city, b.state, b.stars, b.numReviews, b.checkin_number FROM Business b, main_busi mb WHERE b.bid = mb.bid AND mb.mcname = '%s')",selectedMainCatg[j]));
                        if (selectedRelation == "AND"){
                            sb.append("INTERSECT");
                        } else {
                            sb.append("UNION");
                        }
                    } else {
                        for (int i = 0; i < selectedSubCatg.length; i++) {
                            if (selectedAttributes == null || selectedAttributes.length == 0) {
                                sb.append(String.format("(SELECT Distinct b.bid, b.addr, b.city, b.state, b.stars, b.numReviews, b.checkin_number FROM Business b, main_busi mb, sub_busi sb, attri_busi ab WHERE b.bid = mb.bid AND b.bid = sb.bid AND b.bid = ab.bid AND mb.mcname = '%s' AND sb.scname = '%s')",selectedMainCatg[j], selectedSubCatg[i]));
                                if (selectedRelation == "AND"){
                                    sb.append("INTERSECT");
                                } else {
                                    sb.append("UNION");
                                }
                            } else {
                                for (int k = 0; k < selectedAttributes.length; k++) {
                                    sb.append(String.format("(SELECT Distinct b.bid, b.addr, b.city, b.state, b.stars, b.numReviews, b.checkin_number FROM Business b, main_busi mb, sub_busi sb, attri_busi ab WHERE b.bid = mb.bid AND b.bid = sb.bid AND b.bid = ab.bid AND mb.mcname = '%s' AND sb.scname = '%s' AND ab.avalue = '%s')",selectedMainCatg[j], selectedSubCatg[i], selectedAttributes[k]));
                                    if (selectedRelation == "AND"){
                                        sb.append("INTERSECT");
                                    } else {
                                        sb.append("UNION");
                                    }
                                }
                            }
                        }
                    }
                }
                sb.setLength(sb.length() - (selectedRelation == "AND" ? 9 : 5));
                //System.out.println(sb.toString());
                // Reset tableModel
                int rowCount = tableModel.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    tableModel.removeRow(i);
                }

                if (selectedLocation.equals("ALL")) {
                    try {
                        PreparedStatement statement = connection.prepareStatement(sb.toString());
                        ResultSet rs = statement.executeQuery();
                        while (rs.next()) {
                            if(fitTime(rs.getString(1))) {
                                tableModel.insertRow(tableModel.getRowCount(), new String[]{rs.getString(1), rs.getString(2), rs.getString(3),
                                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
                            }
                        }
                    } catch (SQLException se) {
                        System.out.println("SQLException occurs: " + se);
                    }
                } else {
                    String[] str = selectedLocation.split(",");
                    chooseCity = str[0];
                    chooseState = str[1];
                    try {
                        PreparedStatement statement = connection.prepareStatement(sb.toString());
                        ResultSet rs = statement.executeQuery();
                        while (rs.next()) {
                            if (chooseCity.equals(rs.getString("city")) && chooseState.equals(rs.getString("state")) && fitTime(rs.getString(1))) {
                                tableModel.insertRow(tableModel.getRowCount(), new String[]{rs.getString(1), rs.getString(2), rs.getString(3),
                                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
                            }
                        }
                    } catch (SQLException se) {
                        System.out.println("SQLException occurs: " + se);
                    }
                }

                numResult.setText(String.valueOf(tableModel.getRowCount()));
            }
        });
    }

    private static boolean fitTime(String bid){
        if (selectedStartTime == null || selectedStartTime.length() == 0 || selectedEndTime == null || selectedEndTime.length() == 0) return true;
        StringBuilder sb = new StringBuilder();
        String start = "", end = "";
        sb.append(String.format("(SELECT DISTINCT bh.open, bh.close FROM BusinessHour bh WHERE bh.bid = '%s' AND bh.day = '%s')",bid, selectedDay));
        try {
            PreparedStatement statement = connection.prepareStatement(sb.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                start = rs.getString(1);
                end = rs.getString(2);
            }
        } catch (SQLException se) {
            System.out.println("SQLException occurs: " + se);
        }
        if (start.length() == 0 || end.length() == 0) return true;
        String start1 = start.substring(0,2) + start.substring(3,5);
        String end1 = end.substring(0,2) + end.substring(3,5);
        String start2 = selectedStartTime.substring(0,2) + selectedStartTime.substring(3,5);
        String end2 = selectedEndTime.substring(0,2) + selectedEndTime.substring(3,5);

        if(start1.compareTo(end2) > 0 || end1.compareTo(start2) < 0) {
            return false;
        }
        return true;
    }

    private static void closeApp() {
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    public static void main(String[] args)  throws Exception {
        // Connect to Database
        //dbConnection();

        // Open Application
        new ui();

        // Initialize the Application
//        initialization();

//        // Set Up Listener
//        relationListener();
//        mainCatgListener();
//        dayListener();
//        hourListener();
//        subCatgListener();
//        locationListener();
//        attributeListener();
//
//        // Set Up Button to Show all the Results
//        showSubDayLoca();
//        showAttribute();
//        showHour();
//        showResult();
//
//        //close application
//        closeApp();
    }
}