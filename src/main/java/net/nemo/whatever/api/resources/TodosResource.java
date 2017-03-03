package net.nemo.whatever.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import java.util.*;

/**
 * Created by tonyshi on 2017/3/3.
 */
@Service
@Path("/")
public class TodosResource {

    @Autowired
    private JedisPool jedisPool;

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/todos.json")
    public Map<String, Object> deleteTodo(@Context Request request, @QueryParam("id") String id){
        Map<String, Object> result = new HashMap<String, Object>();

        if(id == null)
            throw new IllegalArgumentException("parameter `id` is required");

        Jedis jedis = jedisPool.getResource();
        jedis.del(String.valueOf(id));
        jedis.srem("todos", String.valueOf(id));

        result.put("todos", getAllTodos());
        result.put("success", true);

        return result;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/todos.json")
    public Map<String, Object> addTodo(@Context Request request, @QueryParam("id") String id, @QueryParam("text") String text){
        Map<String, Object> result = new HashMap<String, Object>();

        if(id == null || text == null)
            throw new IllegalArgumentException("parameter `id` and `text` are required");

        Jedis jedis = jedisPool.getResource();
        jedis.hset(String.valueOf(id), "text", text);
        jedis.hset(String.valueOf(id), "completed", String.valueOf(Boolean.FALSE));
        jedis.hset(String.valueOf(id), "createdAt", now());
        jedis.sadd("todos", String.valueOf(id));

        result.put("todos", getAllTodos());
        result.put("success", true);

        return result;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/todos.json")
    public Map<String, Object> toggleTodo(@Context Request request, @QueryParam("id") String id){
        Map<String, Object> result = new HashMap<String, Object>();

        if(id == null)
            throw new IllegalArgumentException("parameter `id` is required");

        Jedis jedis = jedisPool.getResource();
        boolean completed = Boolean.valueOf(jedisPool.getResource().hget(String.valueOf(id), "completed"));
        jedis.hset(String.valueOf(id), "completed", String.valueOf(!completed));

        result.put("todos", getAllTodos());
        result.put("success", true);

        return result;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/todos.json")
    public Map<String, Object> findTodos(){
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("todos", getAllTodos());
        result.put("success", true);

        return result;
    }

    private List<Map<String, Object>> getAllTodos() {
        List<Map<String, Object>> todos = new ArrayList<>();

        for (String id : jedisPool.getResource().smembers("todos")) {
            Map<String, String> r = jedisPool.getResource().hgetAll(id);

            Map<String, Object> todo = new HashMap<String, Object>();
            todo.put("id", id);
            todo.put("completed", Boolean.valueOf(r.get("completed")));
            todo.put("text", r.get("text"));
            todo.put("createdAt", Long.valueOf(r.get("createdAt")));
            todos.add(todo);
        }

        Collections.sort(todos, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> todo1, Map<String, Object> todo2) {
                Long l = ((Long) todo1.get("createdAt") - (Long) todo2.get("createdAt"));
                return l.intValue();
            }
        });

        return todos;
    }

    private String now() {
        return String.valueOf(new Date().getTime());
    }
}
