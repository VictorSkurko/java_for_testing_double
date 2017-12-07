package ru.skurko.addressbook.test.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.skurko.addressbook.test.model.GroupData;
import sun.nio.cs.Surrogate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

    //ПОмечаекм атрибуты аннотациями для фреймворка jcommander
    @Parameter(names = "-c", description = "GroupCount")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;


    public static void main(String[] args) throws IOException {

        GroupDataGenerator generator = new GroupDataGenerator();
        JCommander jCommander = new JCommander(generator);

        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }

        generator.run();
    }

    private void run() throws IOException {
        List<GroupData> groups = generateGroup(count);
        save(groups, new File(file));
    }

    private void save(List<GroupData> groups, File file) throws IOException {

        System.out.println(new File(".").getAbsolutePath());

        Writer writer = new FileWriter(file);
        for (GroupData group : groups) {
            writer.write(String.format("%s;%s;%s\n",
                    group.getGroupName(),
                    group.getGroupHeader(),
                    group.getGroupFooter()));
        }
        writer.close();
    }

    private List<GroupData> generateGroup(int count) {
        List<GroupData> groups = new ArrayList<GroupData>();
        for (int i = 0; i <count ; i++) {
            groups.add(new GroupData()
                    .withGroupName(String.format("groupName %s", i))
                    .withGroupHeader(String.format("groupHeader %s", i))
                    .withGroupFooter(String.format("groupFooter %s", i)));
        }
        return groups;
    }

}
