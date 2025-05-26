module org.example.kingdomrush {
    requires javafx.controls;
    requires javafx.fxml;
    requires static transitive lombok;
    requires java.sql;
    requires javafx.media;


    opens org.example.kingdomrush to javafx.fxml;
    exports org.example.kingdomrush;
    exports org.example.kingdomrush.view;
    opens org.example.kingdomrush.view to javafx.fxml;
}