import java.util.HashMap;
import java.util.Map;


    public class DataLogic implements IRepository {

        Map<String, Integer> usernameMap = new HashMap<>();

        @Override
        public String storeName(String name, String language) {

            if(usernameMap.containsKey(name)) {
                usernameMap.put(name, usernameMap.get(name) + 1);
            }
            else {
                usernameMap.put(name, 1);
            }
            return usernameMap.get(name) + " " + language;
        }

        public Integer addedName(String name){
            if(!usernameMap.containsKey(name)) {
                return 0;
            }
            return usernameMap.get(name);
        }

        public Map<String, Integer> userList(){

            return usernameMap;
        }

        public String clear(){
            usernameMap.clear();
            return "All users cleared successfully";
        }

        public String clearName(String name){

            if(usernameMap.containsKey(name)){
                usernameMap.remove(name);
                return name + " cleared from system";
            }
            else { return name + " " +"is not in the system\n"; }
        }

        public int counter(){
            return usernameMap.size();
        }

        public Integer counterName(String name){
            if (usernameMap.containsKey(name)) {
                return usernameMap.get(name);
            }
            return 0;
        }
        public void exit(){
            System.exit(0);
        }
    }

