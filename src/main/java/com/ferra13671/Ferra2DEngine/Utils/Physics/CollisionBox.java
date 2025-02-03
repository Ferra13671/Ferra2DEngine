package com.ferra13671.Ferra2DEngine.Utils.Physics;

import com.ferra13671.Ferra2DEngine.Utils.Math.FerraMath;
import com.ferra13671.Ferra2DEngine.Utils.Math.Vector3d;

//Yes, Y on 2D
public class CollisionBox {
   private float epsilon = 0.0F;
   public double minX;
   public double minY;
   public double minZ;
   public double maxX;
   public double maxY;
   public double maxZ;

   public CollisionBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      this.minX = minX;
      this.minY = minY;
      this.minZ = minZ;
      this.maxX = maxX;
      this.maxY = maxY;
      this.maxZ = maxZ;
   }

   public CollisionBox expand(double xa, double ya, double za) {
      double _minX = this.minX;
      double _minY = this.minY;
      double _minZ = this.minZ;
      double _maxX = this.maxX;
      double _maxY = this.maxY;
      double _maxZ = this.maxZ;
      if (xa < 0.0F) {
         _minX += xa;
      }

      if (xa > 0.0F) {
         _maxX += xa;
      }

      if (ya < 0.0F) {
         _minY += ya;
      }

      if (ya > 0.0F) {
         _maxY += ya;
      }

      if (za < 0.0F) {
         _minZ += za;
      }

      if (za > 0.0F) {
         _maxZ += za;
      }

      return new CollisionBox(_minX, _minY, _minZ, _maxX, _maxY, _maxZ);
   }

   public CollisionBox grow(double xa, double ya, double za) {
      double _minX = minX - xa;
      double _minY = minY - ya;
      double _minZ = minZ - za;
      double _maxX = maxX + xa;
      double _maxY = maxY + ya;
      double _maxZ = maxZ + za;
      return new CollisionBox(_minX, _minY, _minZ, _maxX, _maxY, _maxZ);
   }

   public boolean checkCollide(CollisionBox c, double xa, double ya, double za) {
      if (c.maxX > minX + xa && c.minX < maxX + xa) {
         if (c.maxY > minY + ya && c.minY < maxY + ya) {
            return c.maxZ >= minZ + za && c.minZ <= maxZ + za;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean isCollidedHorizontal(CollisionBox c) {
      if (c.maxX > minX && c.minX < maxX) {
          return c.maxY > minY && c.minY < maxY;
      } else {
         return false;
      }
   }

   public boolean isCollidedVertical(CollisionBox c) {
      if (c.maxY > minY && c.minY < maxY) {
         return c.maxX > minX && c.minX < maxX;
      } else {
         return false;
      }
   }

   public boolean intersects(CollisionBox c) {
      if (c.maxX > minX && c.minX < maxX) {
         if (c.maxY > minY && c.minY < maxY) {
            return c.maxZ >= minZ && c.minZ <= maxZ;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void move(double xa, double ya, double za) {
      this.minX += xa;
      this.minY += ya;
      this.minZ += za;
      this.maxX += xa;
      this.maxY += ya;
      this.maxZ += za;
   }

   public Vector3d getCenter() {
      return new Vector3d(FerraMath.lerp(0.5, this.minX, this.maxX), FerraMath.lerp(0.5, this.minY, this.maxY), FerraMath.lerp(0.5, this.minZ, this.maxZ));
   }
}
