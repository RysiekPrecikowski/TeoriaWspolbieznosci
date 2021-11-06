// Teoria Współbieżnośi, implementacja problemu 5 filozofów w node.js
// Opis problemu: http://en.wikipedia.org/wiki/Dining_philosophers_problem
//   https://pl.wikipedia.org/wiki/Problem_ucztuj%C4%85cych_filozof%C3%B3w
// 1. Dokończ implementację funkcji podnoszenia widelca (Fork.acquire).
// 2. Zaimplementuj "naiwny" algorytm (każdy filozof podnosi najpierw lewy, potem
//    prawy widelec, itd.).
// 3. Zaimplementuj rozwiązanie asymetryczne: filozofowie z nieparzystym numerem
//    najpierw podnoszą widelec lewy, z parzystym -- prawy.
// 4. Zaimplementuj rozwiązanie z kelnerem (według polskiej wersji strony)
// 5. Zaimplementuj rozwiążanie z jednoczesnym podnoszeniem widelców:
//    filozof albo podnosi jednocześnie oba widelce, albo żadnego.
// 6. Uruchom eksperymenty dla różnej liczby filozofów i dla każdego wariantu
//    implementacji zmierz średni czas oczekiwania każdego filozofa na dostęp
//    do widelców. Wyniki przedstaw na wykresach.
const { performance } = require('perf_hooks');
var Fork = function () {
    this.state = 0;
    return this;
}

Fork.prototype.acquire = function (cb) {
    // zaimplementuj funkcję acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwszą próbą podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy próba jest nieudana, zwiększa czas oczekiwania dwukrotnie
    //    i ponawia próbę, itd.

    var fork = this;

    var loop = function (i) {
        let waitingTime = Math.random() * i;

        setTimeout(function () {
            if (fork.state === 0) {
                fork.state = 1;
                cb();
            } else {
                loop(i * 2);
            }
        }, waitingTime);
    };


    loop(1);
}

Fork.prototype.release = function () {
    this.state = 0;

}

var Philosopher = function (id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    return this;
}



Philosopher.prototype.startNaive = function (count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    let eatingTime = 1;

    let waitingTime = 0;
    let n = count;
    let loop = function (count) {
        if(count > 0) {
            let s = performance.now();
            forks[f1].acquire(function () {
                console.log("filozof " + id + " ma lewy");

                forks[f2].acquire(function () {
                    console.log("filozof " + id + " ma prawy");
                    let t = performance.now();

                    waitingTime += t - s
                    setTimeout(function () {
                        forks[f1].release();
                        forks[f2].release();

                        console.log("filozof " + id + " skonczyl jesc");

                        loop(count - 1);
                    }, eatingTime)
                })
            })
        }

        if (count === 0){
            // console.log("filozof", id, "zjadl wszystko, czekal", waitingTime)
            process.stdout.write(waitingTime/n + ", ")
        }
    };

    setTimeout(function () {loop(count)}, Math.floor(Math.random() * 15));
}

Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    // zaimplementuj rozwiązanie asymetryczne
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców
    if (id % 2 === 0){
        [f1, f2] = [f2, f1];
    }
    let waitingTime = 0;
    let eatingTime = 1;
    let n = count;

    let loop = function (count) {
        if(count > 0) {
            let s = performance.now();
            forks[f1].acquire(function () {
                // console.log("filozof " + id + " ma " + f1);

                forks[f2].acquire(function () {
                    // console.log("filozof " + id + " ma " + f2);
                    let t = performance.now();

                    waitingTime += t - s


                    setTimeout(function () {
                        forks[f1].release();
                        forks[f2].release();

                        // console.log("filozof " + id + " skonczyl jesc");

                        loop(count - 1);
                    }, eatingTime)
                })
            })
        }
        if (count === 0){
            // console.log("filozof", id, "zjadl wszystko, czekal", waitingTime)
            process.stdout.write(waitingTime/n + ", ")
        }
    }
    loop(count)

}

var Conductor = function (n) {
    this.free = n - 1;

    this.waitingCallbacks = []
    return this;
}

Conductor.prototype.acquire = function (id, cb) {
    let conductor = this;
    if (this.free > 0){
        conductor.free -= 1
        setTimeout(cb)
    } else {
        this.waitingCallbacks.push(cb)
    }
}

Conductor.prototype.release = function (id) {
    this.free += 1;
    // console.log("wypuszczam", id)

    if (this.waitingCallbacks.length > 0 && this.free > 0) {
        let popped = this.waitingCallbacks.pop()

        this.free -= 1;
        setTimeout(popped)
    }
}

Philosopher.prototype.startConductor = function(count, conductor) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    let n = count;

    let eatingTime = 1;
    let waitingTime = 0;
    let loop = function (count) {
        let s = performance.now();
        if (count > 0) {

            conductor.acquire(id,function () {

                forks[f1].acquire(function () {
                    // console.log("filozof " + id + " ma " + "lewy");

                    forks[f2].acquire(function () {
                        // console.log("filozof " + id + " ma " + "prawy");

                        let t = performance.now();

                        waitingTime += t - s
                        setTimeout(function () {
                            forks[f1].release();
                            forks[f2].release();
                            conductor.release(id);

                            // console.log("filozof " + id + " skonczyl jesc");

                            loop(count - 1);
                        }, eatingTime)
                    })
                })
            })
        }
        if (count === 0) {
            // console.log("filozof", id, "zjadl wszystko, czekal", waitingTime)
            process.stdout.write(waitingTime/n + ", ")
        }
    }
    loop(count)
}




// TODO: wersja z jednoczesnym podnoszeniem widelców
// Algorytm BEB powinien obejmować podnoszenie obu widelców,
// a nie każdego z osobna



function acquireTwoForks (f1, f2, cb) {

    let loop = function (i) {
        let waitingTime = Math.random() * i;

        setTimeout(function () {
            if (f1.state === 0 && f2.state === 0) {
                f1.state = 1;
                f2.state = 1;
                cb()
            } else {

                loop(i * 2);
            }
        }, waitingTime);
    };

    loop(1)
}

Philosopher.prototype.startTwoSameTime = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    let waitingTime = 0;
    let eatingTime = 1;

    let n = count;


    let loop = function (count) {
        if (count > 0) {
            let s = performance.now();

            acquireTwoForks(forks[f1], forks[f2], function () {
                // console.log("filozof " + id + " ma " + f1);
                // console.log("filozof " + id + " ma " + f2);
                let t = performance.now();

                waitingTime += t - s

                setTimeout(function () {
                    forks[f1].release();
                    forks[f2].release();

                    // console.log("filozof " + id + " skonczyl jesc");

                    loop(count - 1);
                }, eatingTime)

            })
        }
        if (count === 0) {
            // console.log("filozof", id, "zjadl wszystko, czekal", waitingTime)
            process.stdout.write(waitingTime/n + ", ")
        }
    }
    loop(count)

}


var N = 10;
let forks = [];
let philosophers = []
for (let i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (let i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

let conductor = new Conductor(N)
for (let i = 0; i < N; i++) {
    philosophers[i].startAsym(10);
}


