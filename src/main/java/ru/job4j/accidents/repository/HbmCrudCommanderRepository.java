package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class HbmCrudCommanderRepository {
    private final SessionFactory sessionFactory;

    public void run(Consumer<Session> command) {
        tx(session -> {
            command.accept(session);
            return null;
        });
    }

    public void run(String query, Map<String, Object> parameters) {
        Consumer<Session> command = session -> {
            var sq = session.createQuery(query);
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                sq.setParameter(parameter.getKey(), parameter.getValue());
            }
            sq.executeUpdate();
        };
        run(command);
    }

    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> {
            return session.createQuery(query, cl).list();
        };
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl, Map<String, Object> parameters) {
        Function<Session, List<T>> command = session -> {
            Query<T> q = session.createQuery(query, cl);
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                q.setParameter(parameter.getKey(), parameter.getValue());
            }
            return q.list();
        };
        return tx(command);
    }

    public <T> Optional<T> optional(String query, Class<T> cl,
                                    Map<String, Object> parameters) {
        Function<Session, Optional<T>> command = session -> {
            Query<T> q = session.createQuery(query, cl);
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                q.setParameter(parameter.getKey(), parameter.getValue());
            }
            return Optional.ofNullable(q.getSingleResult());
        };
        return tx(command);
    }

    public Integer executeUpdate(String query, Map<String, Object> parameters) {
        Function<Session, Integer> command = session -> {
            var q = session.createQuery(query);
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                q.setParameter(parameter.getKey(), parameter.getValue());
            }
            return q.executeUpdate();
        };
        return tx(command);
    }

    public <T> T tx(Function<Session, T> command) {
        var session = sessionFactory.openSession();
        try {
            var tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            var tx = session.getTransaction();
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}
