package task.Hotel;

import java.io.Serializable;

public abstract class BaseEntity<ID> implements Serializable {
    public ID id;

    public ID getId() {
        return id;
    };
    public void setId(ID id) {
        this.id = id;
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity<?> that = (BaseEntity<?>) o;
        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
