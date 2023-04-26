package org.ktfoms.med.dto;

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


//        XmlMapper xmlMapper = new XmlMapper();
//        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
//        File file = new File("D:\\new.xml");
//        xmlMapper.writeValue(file, spr);
//        xmlMapper.writeValue(output, spr);