package epam.zlatamigas.surveyplatform.model.entity;

public class Theme extends AbstractEntity {
    private int themeId;
    private String themeName;
    private ThemeStatus themeStatus;

    public Theme() {
        themeId = -1;
        themeStatus = ThemeStatus.WAITING;
    }

    public Theme(int themeId) {
        this.themeId = themeId;
    }

    public Theme(String themeName, ThemeStatus themeStatus) {
        this(-1, themeName, themeStatus);
    }

    public Theme(int themeId, String themeName, ThemeStatus themeStatus) {
        this.themeId = themeId;
        this.themeName = themeName;
        this.themeStatus = themeStatus;
    }


    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public ThemeStatus getThemeStatus() {
        return themeStatus;
    }

    public void setThemeStatus(ThemeStatus themeStatus) {
        this.themeStatus = themeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Theme theme = (Theme) o;
        return themeId == theme.themeId
                && themeName != null
                && themeName.equals(theme.themeName)
                && themeStatus == theme.themeStatus;
    }

    @Override
    public int hashCode() {
        int seed = 31;
        int hash = 1;

        hash = seed * hash + themeId;
        hash = seed * hash + (themeName != null ? themeName.hashCode() : 0);
        hash = seed * hash + (themeStatus != null ? themeStatus.hashCode() : 0);

        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Theme{");
        sb.append("themeId=").append(themeId);
        sb.append(", themeName='").append(themeName).append('\'');
        sb.append(", themeStatus=").append(themeStatus);
        sb.append('}');
        return sb.toString();
    }
}
