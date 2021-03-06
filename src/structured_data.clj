(ns structured-data)

(defn do-a-thing [x]
  (let [dbl (+ x x)]
    (Math/pow dbl dbl)))

(defn spiff [v]
  (if (<= 3 (count v))
    (+ (first v) (nth v 2))
    '?))

(defn cutify [v]
  (conj v "<3"))

(defn spiff-destructuring [v]
  (if (<= 3 (count v))
    (let [[fst _ trd] v]
      (+ fst trd))
    '?))

(defn point [x y]
  [x y])

(defn rectangle [bottom-left top-right]
  [bottom-left top-right])

(defn width [rectangle]
  (let [[[x1 y1][x2 y2]] rectangle]
    (- x2 x1)
    ))

(defn height [rectangle]
 (let [[[x1 y1][x2 y2]] rectangle]
    (- y2 y1)))

(defn square? [rectangle]
  (= (height rectangle) (width rectangle)))

(defn area [rectangle]
  (let [w (width rectangle)
        h (height rectangle)]
    (* w h)))

(defn contains-point? [rectangle point]
    (let [[[x1 y1][x2 y2]] rectangle
          [x y] point]
      (and (<= (min x1 x2) x (max x1 x2)) (<= (min y1 y2) y (max y1 y2)))))

(defn contains-rectangle? [outer inner]
  (let [[inner-bottom-left inner-top-right] inner]
    (and (contains-point? outer inner-bottom-left) (contains-point? outer inner-top-right))))

(defn title-length [book]
  (count (:title book)))

(defn author-count [book]
  (count (:authors book)))

(defn multiple-authors? [book]
  (< 1 (author-count book)))

(defn add-author [book new-author]
  (let [authors (:authors book)
        new-authors (conj authors new-author)]
    (assoc book :authors new-authors)))

(defn alive? [author]
  ((complement contains?) author :death-year))

(defn element-lengths [collection]
  (map count collection))

(defn second-elements [collection]
  (map second collection))

(defn titles [books]
  (map :title books))

(defn monotonic? [a-seq]
  (or (apply <= a-seq) (apply >= a-seq)))

(defn stars [n]
  (apply str (repeat n "*")))

(defn toggle [a-set elem]
  (if (contains? a-set elem)
    (disj a-set elem)
    (conj a-set elem)))

(defn contains-duplicates? [a-seq]
  (not (= (count a-seq) (count (set a-seq)))))

(defn old-book->new-book [book]
  (assoc book :authors (set (:authors book))))

(defn has-author? [book author]
  (contains? (:authors book) author))

(defn authors [books]
  (apply clojure.set/union (map :authors books)))

(defn all-author-names [books]
  (set (map :name (authors books))))

(defn author->string [author]
  (let [author-name (:name author)
        author-birthyear (:birth-year author)
        author-deathyear (:death-year author)
        author-years (if author-birthyear (str " (" author-birthyear " - " (if author-deathyear author-deathyear "") ")") "")]
    (str author-name author-years)))

(defn authors->string [authors]
  (apply str (interpose ", " (map author->string authors))))

(defn book->string [book]
  (let [book-title (:title book)
        authors (authors->string (:authors book))]
    (str book-title ", written by " authors)))

(defn books->string [books]
  (let [[cnt bookstrings] (reduce (fn [[cnt acc] book]
                                    (vector (inc cnt)
                                            (conj acc (book->string book)))) [0 []] books)
        allbooks (clojure.string/join (interpose ", " bookstrings))]
    (cond (= cnt 1) (str cnt " book. " allbooks ".")
          (> cnt 1) (str cnt " books. " allbooks ".")
          :else   "No books.")))



(defn books-by-author [author books]
  (filter #(has-author? % author) books))

(defn author-by-name [name authors]
  (first (filter #(= name (:name %)) authors)))

(defn living-authors [authors]
  (filter alive? authors))

(defn has-a-living-author? [book]
  ((complement empty?) (living-authors (:authors book))))

(defn books-by-living-authors [books]
  (filter has-a-living-author? books))

; %________%
