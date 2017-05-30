/**
 * Created by lfs on 2017/5/30.
 */


public class PSO {

    int n=100; //粒子个数，这里取了十个，取太少会出现问题
    Posiotion[] p;
    Posiotion[] v;
    double c1=3;
    double c2=1;
    Posiotion pbest[];
    Posiotion gbest;
    double w=0.3;
    double vmax=0.05;
    public void fitnessFunction(){//适应函数
        for(int i=0;i<n;i++){
            double x=p[i].getX();
            double y=p[i].getY();
            if (x<30&&y<30){
                p[i].setF(30*x-y);
            }else if (x<30&&y>=30){
                p[i].setF(30*y-x);
            }else if (x>=30&&y<30){
                p[i].setF(x*x-y/2);
            }else if (x>=30&&y>=30){
                p[i].setF(20*y*y-500*x);
            }
        }
    }
    public void init(){ //初始化
        p=new Posiotion[n];
        v=new Posiotion[n];
        pbest=new Posiotion[n];
        gbest=new Posiotion(0.0,0.0);
        /***
         * 初始化
         */
        for(int i=0;i<n;i++){
            p[i]=new Posiotion(Math.random()*60,Math.random()*60);
            v[i]=new Posiotion(Math.random()*vmax,Math.random()*vmax);
        }
        fitnessFunction();
        //初始化当前个体极值，并找到群体极值
        gbest.setF(Integer.MIN_VALUE);
        for(int i=0;i<n;i++){
            pbest[i]=p[i];
            if(p[i].getF()>gbest.getF()){
                gbest=p[i];
                gbest.setF(p[i].getF());
            }
        }
        System.out.println("start gbest:"+gbest);
    }
    //粒子群算法
    public void PSO_Method(int max){
        for(int i=0;i<max;i++){
            for(int j=0;j<n;j++){
                //更新位置和速度
                double vx=w*v[j].getX()+c1*Math.random()*(pbest[j].getX()-p[j].getX())+c2*Math.random()*(gbest.getX()-p[j].getX());
                double vy=w*v[j].getY()+c1*Math.random()*(pbest[j].getY()-p[j].getY())+c2*Math.random()*(gbest.getY()-p[j].getY());
                if (vx>vmax) vx=vmax;
                if (vy>vmax) vy=vmax;
//                System.out.println("======"+(i+1)+"======vx:"+vx);
                v[j]=new Posiotion(vx,vy);
//                System.out.println("======"+(i+1)+"======v[j]:"+v[j]);
                p[j].setX(p[j].getX()+v[j].getX());
                p[j].setY(p[j].getY()+v[j].getY());
                //越界判断
                if(p[j].getX()>=60) p[j].setX(59.9);
                if(p[j].getX()<=0) p[j].setX(0.1);
                if(p[j].getY()>=60) p[j].setY(59.9);
                if(p[j].getY()<=0) p[j].setY(0.1);
            }
            fitnessFunction();
            //更新个体极值和群体极值
            for (int j=0;j<n;j++){
                if (pbest[j].getF()<p[j].getF()){
                    pbest[j]=p[j];
                }
                if(p[j].getF()>gbest.getF()){
                    gbest=p[j];
                    gbest.setF(p[j].getF());
                }
            }
            System.out.println("======"+(i+1)+"======gbest:"+gbest.toString());
        }


    }

    class Posiotion{
        private double x;
        private double y;
        private double f;
        Posiotion(double x,double y){
            this.x=x;
            this.y=y;
        }
        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getF() {
            return f;
        }

        public void setF(double f) {
            this.f = f;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public String toString(){
            return " x: "+x+" y: "+y+" f: "+f;
        }
    }

    public static void main(String[] args){
        PSO ts=new PSO();
        ts.init();
        ts.PSO_Method(10000);
    }


}
