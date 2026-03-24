package io.github.martinezdom.repairshop.enums;

public enum RepairStatus {
    PENDIENTE {
        public String toString() {
            return "pendiente";
        }
    },
    EN_PROGRESO {
        public String toString() {
            return "en progreso";
        }
    },
    TERMINADO {
        public String toString() {
            return "terminado";
        }
    }
}
