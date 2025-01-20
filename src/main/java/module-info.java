module org.self.taskmaster {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    requires jdk.compiler;

    opens org.self.taskmaster.models to javafx.base;
    opens org.self.taskmaster to javafx.fxml;
    exports org.self.taskmaster;
}


