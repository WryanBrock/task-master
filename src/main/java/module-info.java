module org.self.taskmaster {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;


    opens org.self.taskmaster to javafx.fxml;
    exports org.self.taskmaster;
}


