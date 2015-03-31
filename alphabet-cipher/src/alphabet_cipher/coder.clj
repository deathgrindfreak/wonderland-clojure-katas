(ns alphabet-cipher.coder)

(defn code-char [f key letter]
  (let [offset (int \a)]
    (char (+ (mod (f (- (int letter) offset)
                     (- (int key) offset))
                  26)
             offset))))

(defn encode [keyword message]
  (apply str
         (map #(code-char + %1 %2)
              (cycle (seq keyword))
              (seq message))))

(defn decode [keyword message]
  (apply str
         (map #(code-char - %1 %2)
              (cycle (seq keyword))
              (seq message))))
