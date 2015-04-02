package SynonymGrouping;

/**
 * Created by keleigong on 3/20/15.
 */
public class test2 {

	public static void main(String[] args){
		Vector v = new Vector();
		Matrix m = new Matrix();
		Vector vm = new Matrix();
		DiagonalMatrix d = new DiagonalMatrix();
		Matrix md = new DiagonalMatrix();
		Vector vd = new DiagonalMatrix();
		IdentityMatrix i = new IdentityMatrix();
		Matrix mi = new IdentityMatrix();
		Vector vi = new IdentityMatrix();
		System.out.println("v.m");
		v.add(m);
		System.out.println("v.vm");
		v.add(vm);
		d.add(vm);
		d.add(md);
		vm.add(md);
		i.add(d);
		i.add(mi);
		m.add(vi);
		DiagonalMatrix.add(mi, md);
		m = Matrix.add(mi, d);
	}
}


class Vector {

	/*1*/  public Vector add(Vector other) {
		System.out.println(1);
		return other;
	}
	/*2*/  public static Vector add(Vector first, Vector second) {
		System.out.println(2);
		return first;
	}
}

class Matrix extends Vector {

	/*3*/  public Matrix add(Matrix other) { System.out.println(3);
	return other;};
	/*4*/  public static Matrix add(Matrix first, Matrix second) { System.out.println(4);
	return first;};
}

class DiagonalMatrix extends Matrix {

	/*5*/  public DiagonalMatrix add(DiagonalMatrix other) {
		System.out.println(5);
		return other;}
	/*6*/  public DiagonalMatrix add(Vector other) {
		System.out.println(6);
		return new DiagonalMatrix();}
	/*7*/  public DiagonalMatrix add(Matrix other) {
		System.out.println(7);
		return (DiagonalMatrix) other;};
	/*8*/  public static DiagonalMatrix add(DiagonalMatrix first, DiagonalMatrix second) { System.out.println(8);
	return first;};
}

class IdentityMatrix extends Matrix {

	/*9*/   public IdentityMatrix add(IdentityMatrix other) { System.out.println(9);
	return other;};
	/*10*/  public static IdentityMatrix add(IdentityMatrix first, IdentityMatrix second) { System.out.println(10);
	return first;};
}