package gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * there might be  more than one rule realization
 */
public class RuleRegistry {

    Map<String, List<Rule>> rules = new HashMap<>();
    List<Rule> ruleList = new ArrayList<>();
    
    void add(Rule rule) {
        if (ruleList.contains(rule))
            return;

        ruleList.add(rule);
        if (!rules.containsKey(rule.getName()))
            this.rules.put(rule.getName(), new ArrayList<>());
        List<Rule> list = this.rules.get(rule.getName());
        list.add(rule);
    }

    public Rule getRuleByName(String name) {
        if (! this.rules.containsKey(name))
            return null;
        final List<Rule> rules = this.rules.get(name);
        assert (rules.size() == 1);
        return rules.get(0);
    }

    public List<Rule> getRulesByName(String name) {
        final List<Rule> rules = this.rules.get(name);
        return rules;
    }
}
