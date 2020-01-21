package firemni_system.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "logins")
public class Login extends NamedEntity{

    @Column(name = "password")
    @NotEmpty
    private String password;

    public Login() {
    }

    public Login(@NotEmpty String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
