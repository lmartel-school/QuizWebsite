package question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A fairly simple Map class designed for simplifying
 * the relationship between a question and its attributes.
 * 
 * Maps the attribute type (as a String) to a list of attributes with that type.
 * @author Leo
 *
 */
public class AttributeMap implements Map<String, List<QuestionAttribute>> {
	
	private Map<String, List<QuestionAttribute>> attrs;

	public AttributeMap(){
		attrs = new HashMap<String, List<QuestionAttribute>>();
	}
	
	/**
	 * Creates a new question attribute from the given parameters and adds it to the attribute map.
	 * @param question_id
	 * @param attrType
	 * @param attrValue
	 * @return
	 */
	public List<QuestionAttribute> putNew(Question question, Question.QUESTION_ATTRIBUTE attrType, String attrValue){
		return putNew(question, attrType.toString(), attrValue);
	}
	private List<QuestionAttribute> putNew(Question question, String attrType, String attrValue) {
		QuestionAttribute newAttr = new QuestionAttribute(question, attrType, attrValue);
		return put(attrType, newAttr);
	}

	/**
	 * Allows client to put a single item into the map, to be appended to the list of values.
	 * @param attrType
	 * @param attrValue
	 * @return
	 */
	public List<QuestionAttribute> put(Question.QUESTION_ATTRIBUTE attrType, QuestionAttribute attrValue){
		return put(attrType.toString(), attrValue);
	}
	public List<QuestionAttribute> put(String attrType, QuestionAttribute attrValue){
		List<QuestionAttribute> values;
		if(attrs.containsKey(attrType)){
			values = attrs.get(attrType);
		} else values = new ArrayList<QuestionAttribute>();
		values.add(attrValue);
		attrs.put(attrType, values);
		return values;
	}
	
	/**
	 * Puts by appending to the old list instead of replacing.
	 */
	public List<QuestionAttribute> put(Question.QUESTION_ATTRIBUTE attrType, List<QuestionAttribute> newValues){
		return put(attrType.toString(), newValues);
	}
	@Override
	public List<QuestionAttribute> put(String key, List<QuestionAttribute> newValues) {
		if(attrs.containsKey(key)){
			List<QuestionAttribute> values = attrs.get(key);
			values.addAll(newValues);
			return values;
		} else return attrs.put(key, newValues);
	}
	
	/**
	 * Returns a list of all the question's attributes.
	 * @return
	 */
	public List<QuestionAttribute> getAll(){
		List<QuestionAttribute> all = new ArrayList<QuestionAttribute>();
		for(Entry<String, List<QuestionAttribute>> e : attrs.entrySet()){
			all.addAll(e.getValue());
		}
		return all;
	}
	
	/**
	 * Returns the first attribute of the given type when you don't want a List<>. 
	 * Useful for simplification when we're guaranteed to have only
	 * one attribute of the type, e.g. the prompt.
	 * @param type
	 * @return
	 */
	public QuestionAttribute getFirst(Question.QUESTION_ATTRIBUTE type){
		return getFirst(type.toString());
	}
	
	public QuestionAttribute getFirst(String type){
		List<QuestionAttribute> values = this.get(type);
		if(values != null && values.size() > 0){
			return values.get(0);
		}
		return null;
	}
	
	public List<QuestionAttribute> get(Question.QUESTION_ATTRIBUTE type){
		return attrs.get(type.toString());
	}
	
	public boolean containsKey(Question.QUESTION_ATTRIBUTE type){
		return attrs.containsKey(type.toString());
	}
	
	public List<QuestionAttribute> remove(Question.QUESTION_ATTRIBUTE type){
		return attrs.remove(type.toString());
	}

	
	/* Begin delegates for Map interface */
	
	@Override
	public void clear() {
		attrs.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return attrs.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return attrs.containsValue(value);
	}

	@Override
	public Set<Map.Entry<String, List<QuestionAttribute>>> entrySet() {
		return attrs.entrySet();
	}
	
	@Override
	public List<QuestionAttribute> get(Object key) {
		return attrs.get(key);
	}

	@Override
	public boolean isEmpty() {
		return attrs.isEmpty();
	}

	@Override
	public Set keySet() {
		return attrs.keySet();
	}

	@Override
	public void putAll(Map m) {
		attrs.putAll(m);
	}


	@Override
	public int size() {
		return attrs.size();
	}

	@Override
	public Collection values() {
		return attrs.values();
	}

	@Override
	public List<QuestionAttribute> remove(Object key) {
		return attrs.remove(key);
	}
	
}
