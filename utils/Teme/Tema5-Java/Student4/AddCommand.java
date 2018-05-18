
public class AddCommand implements Cmd {

    String type, name, path, year, author1, author2, author3;

    public AddCommand(String[] params) {
        name = "";
        path = "";
        year = "";
        author1 = "";
        author2 = "";
        author3 = "";
        int i;
        switch (params[1].toString()) {
            case "book":
                type = "book";
                i = 2;
                name += params[2].toString();
                if (name.charAt(0) != name.charAt(name.length() - 1)) {
                    for (i = 3; i < params.length; i++) {
                        if (params[i].contains("\"")) {
                            name += " " + params[i].toString();
                            break;
                        } else {
                            name += params[i].toString();
                        }
                    }
                }
                path = params[++i].toString();
                year = params[++i].toString();
                author1 = params[++i].toString();
                for (i = i + 1; i < params.length; i++) {
                    if (params[i].contains("\"")) {
                        author1 += " " + params[i].toString();
                        break;
                    } else {
                        author1 += " " + params[i].toString();
                    }
                }
                name = name.split("\"")[1];
                path = path.split("\"")[1];
                author1 = author1.split("\"")[1];
                break; //end add book
            case "article":
                type = "article";
                i = 2;
                name += params[2].toString();
                if (name.charAt(0) != name.charAt(name.length() - 1)) {
                    for (i = 3; i < params.length; i++) {
                        if (params[i].contains("\"")) {
                            name += " " + params[i].toString();
                            break;
                        } else {
                            name += params[i].toString();
                        }
                    }
                }
                path = params[++i].toString();
                year = params[++i].toString();
                author1 = params[++i].toString();
                if (author1.charAt(0) != author1.charAt(author1.length() - 1)) {
                    for (i = i + 1; i < params.length; i++) {
                        if (params[i].contains("\"")) {
                            author1 += " " + params[i].toString();
                            break;
                        } else {
                            author1 += " " + params[i].toString();
                        }
                    }
                }
                author2 = params[++i].toString();
                if (author2.charAt(0) != author2.charAt(author1.length() - 1)) {
                    for (i = i + 1; i < params.length; i++) {
                        if (params[i].contains("\"")) {
                            author2 += " " + params[i].toString();
                            break;
                        } else {
                            author2 += " " + params[i].toString();
                        }
                    }
                }
                author3 = params[++i].toString();
                if (author3.charAt(0) != author3.charAt(author1.length() - 1)) {
                    for (i = i + 1; i < params.length; i++) {
                        if (params[i].contains("\"")) {
                            author3 += " " + params[i].toString();
                            break;
                        } else {
                            author3 += " " + params[i].toString();
                        }
                    }
                }
                name = name.split("\"")[1];
                path = path.split("\"")[1];
                author1 = author1.split("\"")[1];
                author2 = author2.split("\"")[1];
                author3 = author3.split("\"")[1];
                break; //end add article
            case "manual":
                type = "manual";
                i = 2;
                name += params[2].toString();
                if (name.charAt(0) != name.charAt(name.length() - 1)) {
                    for (i = 3; i < params.length; i++) {
                        if (params[i].contains("\"")) {
                            name += " " + params[i].toString();
                            break;
                        } else {
                            name += params[i].toString();
                        }
                    }
                }
                path = params[++i].toString();
                name = name.split("\"")[1];
                path = path.split("\"")[1];
                break; //end add manual
        }
    }
    public String getType()
    {
        return type;
    }
    public String getName()
    {
        return name;
    }
    public String getPath()
    {
        return path;
    }
    public int getYear()
    {
        return Integer.parseInt(year);
    }
    public String getAuthor()
    {
        return author1;
    }
    public String getPublisher()
    {
        return author2;
    }
    public String getRedactor()
    {
        return author3;
    }
}
