module dannewsumstudent.sosbestfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens dannewsumstudent.sosbestfinal to javafx.fxml;
    exports dannewsumstudent.sosbestfinal;
}