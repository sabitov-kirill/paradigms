(defn same-length? [args] (every? #(= (count (first args)) (count %)) (rest args)))
(defn test [coll] (every? true? coll))
(defn by-element [op args] (apply (partial mapv op) args))

; Vectors operations
(defn v? [v] (and (vector? v) (every? number? v)))
(defn vs-same-size? [args] (and (every? v? args) (same-length? args)))
(defn vs-by-element [op] (fn [& args] {:pre [(vs-same-size? args)]} (by-element op args)))
(def v+ (vs-by-element +))
(def v- (vs-by-element -))
(def v* (vs-by-element *))
(def vd (vs-by-element /))
(defn v*s ([v & ss] {:pre [(and (v? v) (every? number? ss))]} (let [sss (reduce * ss)] (mapv (partial * sss) v))))
(defn scalar [& args] {:pre [(vs-same-size? args)]} (reduce + (apply v* args)))
(defn vect
  ([a] {:pre [(v? a)]} a)
  ([a b] {:pre [(vs-same-size? [a b])]} (vector (- (* (nth a 1) (nth b 2)) (* (nth a 2) (nth b 1)))
                                                (- (* (nth a 2) (nth b 0)) (* (nth a 0) (nth b 2)))
                                                (- (* (nth a 0) (nth b 1)) (* (nth a 1) (nth b 0)))))
  ([a b & args] (reduce vect (vect a b) args)))

; Shapeless operations
(defn shapeless-by-element [op]
  (letfn [(impl [a b]
            (if (vector? a)
              (mapv impl a b)
              (op a b)))]
    impl))
(def s+ (shapeless-by-element +))
(def s- (shapeless-by-element -))
(def s* (shapeless-by-element *))
(def sd (shapeless-by-element /))

(s+ 5.3 6.8)

; Matrix operations
(defn m? [m] (and (vector? m) (every? v? m)))
(defn ms-same-size? [args] (and (every? m? args) (test (apply map #(vs-same-size? %&) args)) (same-length? args)))
(defn m-v-same-size? [m v] (and (m? m) (v? v) (test (map #(same-length? [% v]) m))))
(defn transpose [m] {:pre [(m? m)]} (apply mapv vector m))
(defn ms-by-element [op] (fn [& args] {:pre [(ms-same-size? args)]} (by-element #(by-element op %&) args)))
(def m+ (ms-by-element +))
(def m- (ms-by-element -))
(def m* (ms-by-element *))
(def md (ms-by-element /))
(defn m*s [m & ss] {:pre [(and (m? m) (every? number? ss))]} (mapv #(apply v*s % ss) m))
(defn m*v [m v] {:pre [(m-v-same-size? m v)]} (mapv (partial scalar v) m))
(defn m*m
  ([m] {:pre [(m? m)]} m)
  ([A B] {:pre [(and (m? B) (same-length? [(first A) B]))]} (transpose (mapv (partial m*v A) (transpose B))))
  ([A B & args] (reduce m*m (m*m A B) args)))