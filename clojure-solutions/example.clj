(defn hello [name] (str "Hello, " name "!"))
(defn add [& args] (apply + args))
(println (add 1 2 3))