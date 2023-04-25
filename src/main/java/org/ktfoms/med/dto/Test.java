package org.ktfoms.med.dto;

//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(
//        namespace = "http://spring.otus.ru",
//        name = "test-jaxb"
//)
public class Test {

    private Integer id;
//    @XmlElement
    private String name;

    public Test(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
//            , headers = "Accept=application/xml"), headers = { "Content-Type = application/xml" }
//output.write(new String("АБВГДфбвгдё".getBytes(StandardCharsets.UTF_8), "windows-1251"));