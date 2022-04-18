package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

import javax.xml.transform.Result;

public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}
	private String createSelectAllQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		return sb.toString();
	}

	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " = ? ");
		return sb.toString();
	}

	private String createDeleteQuery(String field) {

		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " = ?");
		return sb.toString();
	}

	private String createInsertQuery(T t) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT");
		sb.append(" INTO ");
		sb.append(type.getSimpleName());
		sb.append("(");
		for (Field field : type.getDeclaredFields()) {
			String fieldName = field.getName();
			sb.append(fieldName + ",");
		}
		sb.setLength(sb.length()-1); // stergem ultimu caracter

		sb.append(") VALUES(");

		for (int i=1;i<t.getClass().getDeclaredFields().length;i++) {
				sb.append("?, ");
		}
		sb.append("?)");
		return sb.toString();
	}

	private String createUpdateQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName());
		sb.append(" SET ");
		for (Field f : type.getDeclaredFields()) {
			String fieldName = f.getName();
			sb.append(fieldName + " = ?,");
		}
		sb.setLength(sb.length()-1);
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}


	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectAllQuery();
		List<T> allObjects = new ArrayList<T>();
		try{
			System.out.println("OREDR QUERY: " + query);
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(query);
			allObjects =  createObjects(resultSet);

		}catch(SQLException e){
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return allObjects;
	}

	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T)ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					System.out.println("Filed" + fieldName);
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int insert(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createInsertQuery(t);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			int i = 1;
			for(Field field : t.getClass().getDeclaredFields()){
				String fieldName = field.getName();
				PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
				Method method = propertyDescriptor.getReadMethod();
				Object o = method.invoke(t);
				System.out.println("inserting on " + i + " " + o.toString());
				statement.setObject(i, o);
				i++;
			}
			System.out.println(query);
			statement.executeUpdate();

			int id = -1;
			ResultSet keys = statement.getGeneratedKeys();
			if(keys.next()){
				id =keys.getInt(1);
			}

			return id;
		} catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
		}
		finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return -1;
	}

	public T update(T t) {
		int id;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createUpdateQuery("id");
		try {
			Field f = type.getDeclaredField("id");
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor("id", type);
			int i = 1;
			for(Field field : t.getClass().getDeclaredFields()) {
				String fieldName = field.getName();
				PropertyDescriptor propertyDescriptor2 = new PropertyDescriptor(fieldName, type);
				Method method = propertyDescriptor2.getReadMethod();
				Object o = method.invoke(t);
				System.out.println("inserting on " + i + " " + o.toString());
				statement.setObject(i, o);
				i++;
			}
			PropertyDescriptor propertyDescriptor2 = new PropertyDescriptor("id", type);
			Method method = propertyDescriptor2.getReadMethod();
			Object o = method.invoke(t);
			statement.setObject(i, o);
			System.out.println(query);
			statement.executeUpdate();
			return t;

		}catch(SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
		}
		ConnectionFactory.close(resultSet);
		ConnectionFactory.close(statement);
		ConnectionFactory.close(connection);

		return null;
	}

	public T delete(T t){
		int id;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createDeleteQuery("id");
		try {
				connection = ConnectionFactory.getConnection();
				statement = connection.prepareStatement(query);
			    PropertyDescriptor propertyDescriptor = new PropertyDescriptor("id", type);
			    Method method = propertyDescriptor.getReadMethod();
			    Object o = method.invoke(t);
			    statement.setObject(1, o);
			    statement.executeUpdate();
			}catch(Exception e){
				e.printStackTrace();
			}
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);

		return null;
	}


}
