package negocio.comuns.arquitetura;

public class UsuarioPeriodoAcessoVO{
    private Integer diaSemana;//1: Domingo | 2: Segunda | 3: Terça | 4: Quarta | 5: Quinta | 6: Sexta | 7: Sábado
    private Boolean acessoPermitido;
    private Integer horasInicio;
    private Integer minutosInicio;
    private Integer horasFim;
    private Integer minutosFim;

    public String getDiaSemana_Apresentar() {
        switch (getDiaSemana().intValue()){
            case 1:
                return "Domingo";
            case 2:
                return "Segunda";
            case 3:
                return "Terça";
            case 4:
                return "Quarta";
            case 5:
                return "Quinta";
            case 6:
                return "Sexta";
            case 7:
                return "Sábado";
            default:
                return "";
        }
    }

    public Integer getDiaSemana() {
        if (diaSemana == null) {
            diaSemana = 2;
        }
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Boolean getAcessoExtendeProximoDia() {
        if(getHorasInicio() > getHorasFim()){
            return true;
        }
        if(getHorasInicio() == getHorasFim()){
            if(getMinutosInicio() > getMinutosFim()) {
                return true;
            }
        }
        return false;
    }

    public Integer getHorasInicio() {
        if (horasInicio == null) {
            horasInicio = 8;
        }
        if (!getAcessoPermitido()) {
            horasInicio = 0;
        }
        return horasInicio;
    }

    public void setHorasInicio(Integer horasInicio) {
        this.horasInicio = horasInicio;
    }

    public Integer getMinutosInicio() {
        if (minutosInicio == null) {
            minutosInicio = 0;
        }
        if (!getAcessoPermitido()) {
            minutosInicio = 0;
        }
        return minutosInicio;
    }

    public void setMinutosInicio(Integer minutosInicio) {
        this.minutosInicio = minutosInicio;
    }

    public Integer getHorasFim() {
        if (horasFim == null) {
            horasFim = 18;
        }
        if (!getAcessoPermitido()) {
            horasFim = 0;
        }
        return horasFim;
    }

    public void setHorasFim(Integer horasFim) {
        this.horasFim = horasFim;
    }

    public Integer getMinutosFim() {
        if (minutosFim == null) {
            minutosFim = 0;
        }
        if (!getAcessoPermitido()) {
            minutosFim = 0;
        }
        return minutosFim;
    }

    public void setMinutosFim(Integer minutosFim) {
        this.minutosFim = minutosFim;
    }

    public Boolean getAcessoPermitido() {
        if (acessoPermitido == null) {
            acessoPermitido = true;
        }
        return acessoPermitido;
    }

    public void setAcessoPermitido(Boolean acessoPermitido) {
        this.acessoPermitido = acessoPermitido;
    }
}